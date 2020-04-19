package sample.food;
import javafx.scene.paint.Color;

public class BasicFood extends Food {
    public BasicFood(int positionX, int positionY) {
        super(positionX, positionY);
        setFill(Color.RED);
    }
}
