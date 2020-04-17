package sample;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Field extends Pane {
    int fieldWidth, fieldHeight;
    ArrayList<Block> blocks = new ArrayList<>();
    ExtraFood extraFood;
    Snake snake;
    ArrayList<Food> foods = new ArrayList<>();
    int howManyFood = 1;
    int score = 0;
    int highestScore = 0;
    int speeding = 5;

    public Field(int width, int height) {
        this.fieldWidth = width;
        this.fieldHeight = height;

        setMinSize(width * Main.BLOCK_SIZE, height * Main.BLOCK_SIZE);
        setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        addFood();
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void addFood() {
        int toAdd = howManyFood - foods.size();
        for(int i=0; i<toAdd; i++) {
            Random rand = new Random();
            boolean foodOnSnake;
            int positionX, positionY;
            do {
                foodOnSnake = false;
                positionX = rand.nextInt(fieldWidth - 1);
                positionY = rand.nextInt(fieldHeight - 1);
                for (Block body : blocks) {
                    if (body.positionX == positionX && body.positionY == positionY) {
                        foodOnSnake = true;
                        break;
                    }
                }
            } while (foodOnSnake);
            Food f = new Food(positionX, positionY);
            getChildren().add(f);
            foods.add(f);
        }
    }

    public void removeFood(Food food) {
        getChildren().remove(food);
        foods.remove(food);
    }

    public void removeExtraFood(ExtraFood extraFood) {
        getChildren().remove(extraFood);
        extraFood = null;
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
        if (foods == null) {
            return false;
        }
        updateHighScore();
        for (Food food : foods) {
            if (snake.head.positionX == food.positionX && snake.head.positionY == food.positionY) {
                needMoreSpeed();
                removeFood(food);
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
            positionX = rand.nextInt(fieldWidth - 1);
            positionY = rand.nextInt(fieldHeight - 1);
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
