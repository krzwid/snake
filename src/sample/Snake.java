package sample;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Snake {
    ArrayList<Block> blocks = new ArrayList<Block>();

    Block head;
    Block tail;

    public Snake(int initialLength, Field field) {
        int initialPositionX = field.width/ 2;
        int initialPositionY = field.height/ 2;
        head = new Block(initialPositionX, initialPositionY, null, field);
        blocks.add(head);
        head.setFill(Color.BLUE);

        tail = head;
        for(int i=1; i<initialLength; i++){
            Block body = new Block(initialPositionX-i, initialPositionY, tail, field);
            body.setFill(Color.GREEN);
            blocks.add(body);
            tail = body;
        }
    }

    public void setDirection (Direction direction) {
        head.direction = direction;
    }
}