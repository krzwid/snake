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
        int positionX = rand.nextInt(fieldWidth-1);
        int positionY = rand.nextInt(fieldHeight-1);

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
        }
    }

    private void addBlock(Block body) {
        getChildren().add(body);
        blocks.add(body);
    }

    public boolean isEaten(Food food) {
        if (food == null){
            return false;
        }
        return snake.head.positionX == food.positionX
                && snake.head.positionY == food.positionY;
    }
}
