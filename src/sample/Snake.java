package sample;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    List<Block> blocks = new ArrayList<>();

    Block head;
    Block tail;

    public Snake(int initialLength, Field field) {
        final int initialPositionX = field.width/ 2;
        final int initialPositionY = field.height/ 2;
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