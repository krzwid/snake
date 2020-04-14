package sample;

import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
    int positionX, positionY, oldPositionX, oldPositionY;

    Block previous;

    public Block(int positionX, int positionY, Block previous) {
        super(Main.BLOCK_SIZE, Main.BLOCK_SIZE);
        this.positionX = positionX;
        this.positionY = positionY;

        setTranslateX(this.positionX * Main.BLOCK_SIZE);
        setTranslateY(this.positionY * Main.BLOCK_SIZE);
    }
}
