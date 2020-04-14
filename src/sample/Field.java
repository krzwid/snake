package sample;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Field extends Pane {
    int fieldWidth, fieldHeight;
    ArrayList<Block> blocks = new ArrayList<>();
    Snake snake;

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void addSnake(Snake snake) {
        this.snake = snake;
        for (Block body: snake.blocks) {
            addBlock(body);
        }
    }

    private void addBlock(Block body) {
        getChildren().add(body);
        blocks.add(body);
    }

    public Field(int width, int height) {
        this.fieldWidth = width;
        this.fieldHeight = height;

        setMinSize(width * Main.BLOCK_SIZE, height * Main.BLOCK_SIZE);
        setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
    }
}
