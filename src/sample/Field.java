package sample;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.food.BasicFood;
import sample.food.ExtraFood;
import sample.food.Food;
import sample.food.SlowFood;

import java.util.ArrayList;
import java.util.Random;

public class Field extends Pane {
    static final Random rand = new Random();

    public final int width, height;
    Snake snake;
    ArrayList<Block> blocks = new ArrayList<>();
    ArrayList<Food> foods = new ArrayList<>();
    int howManyFood = 1;
    int score = 0;
    int highestScore = 0;
    int speeding = 5;
    boolean slowed = false;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        setMinSize(width * Main.BLOCK_SIZE, height * Main.BLOCK_SIZE);
        setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        addFood();
    }

    public void addSnake(Snake snake) {
        this.snake = snake;
        for (Block body : snake.blocks) {
            addBlock(body);
        }
    }

    private void addBlock(Block body) {
        getChildren().add(body);
        blocks.add(body);
    }

    public void update() {
        for (Block body : blocks) {
            body.update();
        }
        if (isEaten()) {
            if (score % 5 == 0) howManyFood++;
            if (score % 10 == 0) addExtraFood();
            if (score % 15 == 0 && !slowed) addSlowFood();
            addFood();
            growSnake();
        }
    }

    private void growSnake() {
        Block tail = new Block(snake.tail.oldPositionX, snake.tail.oldPositionY, snake.tail, this);
        tail.setFill(Color.GREEN);
        snake.tail = tail;
        addBlock(tail);
    }

    public boolean isDead() {
        for (Block body : blocks) {
            if (body == snake.head) continue;
            if (snake.head.positionX == body.positionX && snake.head.positionY == body.positionY) {
                return true;
            }
        }
        return false;
    }

    private void updateHighScore() {
        if (score > highestScore) {
            highestScore = score;
        }
    }

    public boolean checkHighestScore() {
        return highestScore != 0 && score == highestScore;
    }

    private void needMoreSpeed() {
        if (score % 3 == 2) {
            speeding += 3;
        }
    }

    private void addFood() {
        int toAdd = howManyFood - foods.size();
        for (int i = 0; i < toAdd; i++) {
            int[] position = drawFoodPosition();
            BasicFood food = new BasicFood(position[0], position[1]);
            getChildren().add(food);
            foods.add(food);
        }
    }

    private void addSlowFood() {
        int[] position = drawFoodPosition();
        SlowFood slowFood = new SlowFood(position[0], position[1]);
        getChildren().add(slowFood);
        foods.add(slowFood);
    }

    private void addExtraFood() {
        int[] position = drawFoodPosition();
        ExtraFood extraFood = new ExtraFood(position[0], position[1]);
        getChildren().add(extraFood);
        foods.add(extraFood);
    }

    private int[] drawFoodPosition() {
        int[] position = new int[2];
        boolean foodOnSnake;
        do {
            foodOnSnake = false;
            position[0] = rand.nextInt(width - 1);
            position[1] = rand.nextInt(height - 1);
            for (Block body : blocks) {
                if (body.positionX == position[0] && body.positionY == position[1]) {
                    foodOnSnake = true;
                    break;
                }
            }
            for (Food food : foods) {
                if (food.positionX == position[0] && food.positionY == position[1]) {
                    foodOnSnake = true;
                    break;
                }
            }
        } while (foodOnSnake);
        return position;
    }

    private boolean isEaten() {
        if (foods == null) return false;

        for (Food food : foods) {
            if (snake.head.positionX == food.positionX && snake.head.positionY == food.positionY) {
                needMoreSpeed();
                removeFood(food);
                score += food.getScore();
                slowed = food.getScore() <= 0;
                speeding += food.getSpeed();
                updateHighScore();
                return true;
            }
        }
        return false;
    }

    private void removeFood(Food food) {
        getChildren().remove(food);
        foods.remove(food);
    }
}