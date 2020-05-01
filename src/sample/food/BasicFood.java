package sample.food;
import javafx.scene.paint.Color;

public class BasicFood extends Food implements IScoreAndSpeed{
    public BasicFood(int positionX, int positionY) {
        super(positionX, positionY);
        setFill(Color.RED);
    }

    @Override
    public int getScore() {
        return 1;
    }
}