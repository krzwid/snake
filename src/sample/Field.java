package sample;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Field extends Pane {
    int fieldWidth, fieldHeight;
    ArrayList<Block> blocks = new ArrayList<>();
    Snake snake;
    Food food;

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
        Random rand = new Random();
        boolean foodOnSnake;
        int positionX, positionY;
        do{
            foodOnSnake = false;
            positionX = rand.nextInt(fieldWidth-1);
            positionY = rand.nextInt(fieldHeight-1);
            for (Block body: blocks){
                if (body.positionX == positionX && body.positionY == positionY) {
                    foodOnSnake = true;
                    break;
                }
            }
        }while(foodOnSnake);
        Food f = new Food(positionX,positionY);
        getChildren().add(f);
        food = f;
    }

    public void removeFood() {
        getChildren().remove(food);
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
        if (isEaten(food)){
            score += 1;
            removeFood();
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

    public boolean isEaten(Food food) {
        if (food == null){
            return false;
        }
        updateHighScore();

        if (snake.head.positionX == food.positionX && snake.head.positionY == food.positionY) {
            needMoreSpeed();

            return true;
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
            speeding += 2;
        }
    }
}
