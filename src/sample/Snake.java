package sample;



import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    ArrayList<Block> blocks = new ArrayList<Block>();

    Block head;

    public Snake(int initialLength, Field field) {
        int initialPositionX = field.getFieldWidth() / 2;
        int initialPositionY = field.getFieldHeight() / 2;
        head = new Block(initialPositionX, initialPositionY, null, field);
        blocks.add(head);
        head.setFill(Color.BLUE);

        Block previous = head;
        for(int i=1; i<initialLength; i++){
            Block body = new Block(initialPositionX-i, initialPositionY, previous, field);
            body.setFill(Color.GREEN);
            blocks.add(body);
            previous = body;
        }
    }

    public void setDirection (Direction direction) {
        head.direction = direction;
    }
}
