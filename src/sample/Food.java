package sample;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
    int positionX, positionY;

    public Food (int positionX, int positionY) {
        super(Main.BLOCK_SIZE, Main.BLOCK_SIZE);
        this.positionX = positionX;
        this.positionY = positionY;
        setTranslateX(this.positionX * Main.BLOCK_SIZE);
        setTranslateY(this.positionY * Main.BLOCK_SIZE);
        setFill(Color.RED);
    }
}
