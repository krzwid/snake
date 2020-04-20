package sample;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.food.BasicFood;
import sample.food.ExtraFood;

import java.util.ArrayList;
import java.util.Random;

public class Field extends Pane {
    public final int width, height;
    ArrayList<Block> blocks = new ArrayList<>();
    ExtraFood extraFood;
    Snake snake;
    ArrayList<BasicFood> basicFoods = new ArrayList<>();
    int howManyFood = 1;
    int score = 0;
    int highestScore = 0;
    int speeding = 5;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        setMinSize(width * Main.BLOCK_SIZE, height * Main.BLOCK_SIZE);
        setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        addFood();
    }

    public void addFood() {
        int toAdd = howManyFood - basicFoods.size();
        for(int i=0; i<toAdd; i++) {
            Random rand = new Random();
            boolean foodOnSnake;
            int positionX, positionY;
            do {
                foodOnSnake = false;
                positionX = rand.nextInt(width - 1);
                positionY = rand.nextInt(height - 1);
                for (Block body : blocks) {
                    if (body.positionX == positionX && body.positionY == positionY) {
                        foodOnSnake = true;
                        break;
                    }
                }
            } while (foodOnSnake);
            BasicFood f = new BasicFood(positionX, positionY);
            getChildren().add(f);
            basicFoods.add(f);
        }
    }

    public void removeFood(BasicFood basicFood) {
        getChildren().remove(basicFood);
        basicFoods.remove(basicFood);
    }

    public void removeExtraFood(ExtraFood extraFood) {
        getChildren().remove(extraFood);
    }

    public void addSnake(Snake snake) {
        this.snake = snake;
        for (Block body: snake.blocks) {
            addBlock(body);
        }
    }

    public void update() {
        for (Block body : blocks) {
            body.update();
        }
        if (isEaten() || isEatenExtraFood()){
            if(score % 5 == 0) {
                howManyFood++;
            }
            if (score % 10 == 0){
                addExtraFood();
            }
            addFood();
            growSnake();
        }
    }

    private void addBlock(Block body) {
        getChildren().add(body);
        blocks.add(body);
    }

    public void growSnake() {
        Block tail = new Block(snake.tail.oldPositionX, snake.tail.oldPositionY, snake.tail, this);
        tail.setFill(Color.GREEN);
        snake.tail = tail;

        addBlock(tail);
    }

    public boolean isEaten() {
        if (basicFoods == null) {
            return false;
        }
        updateHighScore();
        for (BasicFood basicFood : basicFoods) {
            if (snake.head.positionX == basicFood.positionX && snake.head.positionY == basicFood.positionY) {
                needMoreSpeed();
                removeFood(basicFood);
                score+=1;
                return true;
            }

        }
        return false;
    }


    public boolean isDead() {
        for (Block body : blocks) {
            if(body == snake.head) continue;
            if (snake.head.positionX == body.positionX && snake.head.positionY == body.positionY) {
                return true;
            }
        }
        return false;
    }

    public void updateHighScore() {
        if (score > highestScore) {
            highestScore = score;
        }
    }

    public boolean checkHighestScore() {
        return highestScore != 0 && score == highestScore;
    }

    public void needMoreSpeed() {
        if (score % 3 == 2) {
            speeding += 3;
        }
    }

    public void addExtraFood() {
        Random rand = new Random();
        boolean foodOnSnake;
        int positionX, positionY;
        do {
            foodOnSnake = false;
            positionX = rand.nextInt(width - 1);
            positionY = rand.nextInt(height - 1);
            for (Block body : blocks) {
                if (body.positionX == positionX && body.positionY == positionY) {
                    foodOnSnake = true;
                    break;
                }
            }
        } while (foodOnSnake);
        ExtraFood ef = new ExtraFood(positionX, positionY);
        getChildren().add(ef);
        extraFood = ef;
    }

    public boolean isEatenExtraFood() {
        if (extraFood == null) {
            return false;
        }
        updateHighScore();
        if (snake.head.positionX == extraFood.positionX && snake.head.positionY == extraFood.positionY) {
            needMoreSpeed();
            removeExtraFood(extraFood);
            score += 2;
            return true;
        }
        return false;
    }

}