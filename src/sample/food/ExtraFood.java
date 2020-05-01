package sample.food;
import javafx.scene.paint.Color;

public class ExtraFood extends Food implements IScoreAndSpeed{
    public ExtraFood (int positionX, int positionY) {
        super(positionX, positionY);
        setFill(Color.YELLOW);
    }

    @Override
    public int getScore() {
        return 3;
    }
}