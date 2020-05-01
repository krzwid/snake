package sample.food;
import javafx.scene.shape.Rectangle;
import sample.Main;

public abstract class Food extends Rectangle implements IScoreAndSpeed{
    public final int positionX, positionY;
    public Food(int positionX, int positionY) {
        super(Main.BLOCK_SIZE, Main.BLOCK_SIZE);
        this.positionX = positionX;
        this.positionY = positionY;
        setTranslateX(this.positionX * Main.BLOCK_SIZE);
        setTranslateY(this.positionY * Main.BLOCK_SIZE);
    }

    public int getScore() {
        return 0;
    };

    public int getSpeed() {
        return 0;
    };
}