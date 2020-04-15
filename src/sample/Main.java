package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    static final int BLOCK_SIZE = 10;

    int width = 75;
    int height = 50;

    int initialLength = 20;
    long then = System.nanoTime();

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Field field = new Field(width, height);
        field.addSnake(new Snake(initialLength, field));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(now - then > 1000000000/4){
                    field.update();
                    then = now;
                }
            }
        };
        timer.start();

        root.getChildren().add(field);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e->{
            if (e.getCode() == KeyCode.UP && field.snake.head.direction != Direction.SOUTH) {
                field.snake.setDirection(Direction.NORTH);
            }

            if (e.getCode() == KeyCode.DOWN && field.snake.head.direction != Direction.NORTH) {
                field.snake.setDirection(Direction.SOUTH);
            }

            if (e.getCode() == KeyCode.LEFT && field.snake.head.direction != Direction.EAST) {
                field.snake.setDirection(Direction.WEST);
            }

            if (e.getCode() == KeyCode.RIGHT && field.snake.head.direction != Direction.WEST) {
                field.snake.setDirection(Direction.EAST);
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake game");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
