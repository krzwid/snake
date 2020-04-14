package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    static final int BLOCK_SIZE = 10;

    int width = 75;
    int height = 50;

    int initialLength = 20;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Field field = new Field(width, height);
        field.addSnake(new Snake(initialLength, field));
        root.getChildren().add(field);

        Scene scene = new Scene(root);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake game");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
