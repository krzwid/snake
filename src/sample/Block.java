package sample;

import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
    int positionX, positionY, oldPositionX, oldPositionY;
    Direction direction = Direction.EAST;
    Block previous;
    int maximumX, maximumY;
    public Block(int positionX, int positionY, Block previous, Field field) {
        super(Main.BLOCK_SIZE, Main.BLOCK_SIZE);
        this.positionX = positionX;
        this.positionY = positionY;

        setTranslateX(this.positionX * Main.BLOCK_SIZE);
        setTranslateY(this.positionY * Main.BLOCK_SIZE);
        this.previous = previous;
        maximumX = field.fieldWidth-1;
        maximumY = field.fieldHeight-1;
    }

    public void update() {
        oldPositionX = positionX;
        oldPositionY = positionY;

        if (previous == null) {
            switch (direction) {
                case NORTH:
                    moveNorth();
                    break;
                case EAST:
                    moveEast();
                    break;
                case WEST:
                    moveWest();
                    break;
                case SOUTH:
                    moveSouth();
                    break;
            }
        }else{
            positionX = previous.oldPositionX;
            positionY = previous.oldPositionY;
        }
        updatePosition();
    }

    public void moveNorth(){
        positionY--;
        if(positionY < 0) {
            positionY = maximumY;
        }
    }
    public void moveSouth(){
        positionY++;
        if(positionY > maximumY) {
            positionY = 0;
        }
    }
    public void moveEast(){
        positionX++;
        if(positionX > maximumX) {
            positionX = 0;
        }
    }
    public void moveWest(){
        positionX--;
        if(positionX < 0) {
            positionX = maximumX;
        }
    }

    public void updatePosition() {
        setTranslateX(positionX*Main.BLOCK_SIZE);
        setTranslateY(positionY*Main.BLOCK_SIZE);
    }
}
