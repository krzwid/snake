package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class Main extends Application {

    public static final int BLOCK_SIZE = 20;

    int width = 25;
    int height = 25;

    boolean changed = false;

    int initialLength = 2;
    long then = System.nanoTime();
    Field field = new Field(width, height);
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        VBox root = new VBox(10);

        field.addSnake(new Snake(initialLength, field));

        File file = new File("result");
        Scanner scanner = new Scanner(file);
        String first = scanner.nextLine();
        String second = scanner.nextLine();
        String line = scanner.nextLine();
        char znak = line.charAt(2);
        int beginResult = (int) znak - 33;
        field.highestScore = beginResult;


        HBox labels = new HBox(5);
        Label scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("tahoma", 30));

        Label highestScoreLabel = new Label("Highest score: 0" + beginResult);
        highestScoreLabel.setFont(Font.font("tahoma", 30));

        scoreLabel.setAlignment(Pos.TOP_LEFT);
        Region betweenScores = new Region();

        highestScoreLabel.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(betweenScores, Priority.ALWAYS);

        labels.setAlignment(Pos.BASELINE_CENTER);
        if(first.length() == 13 && second.length() == 23 && line.length() == 47){
            labels.getChildren().add(scoreLabel);
            labels.getChildren().add(betweenScores);
            labels.getChildren().add(highestScoreLabel);
        }
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(now - then > 1000000000/field.speeding){
                    field.update();
                    then = now;
                    scoreLabel.setText("Score: " + field.score);
                    changed = false;
                    highestScoreLabel.setText("Highest score: " + field.highestScore);
                    if (field.isDead()) {
                        stop();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Game over!");
                        if (field.checkHighestScore()){
                            try {
                                hashResult(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            alert.setContentText("Congratulations! New highest score: " + field.highestScore);
                        }
                        else {
                            alert.setContentText("Score: " + field.score);
                        }

                        Platform.runLater(alert::showAndWait);

                        alert.setOnHidden(e->{
                            root.getChildren().clear();
                            int highestScore = field.highestScore;
                            field = new Field(width, height);
                            field.addSnake(new Snake(initialLength, field));
                            field.highestScore = highestScore;
                            scoreLabel.setText("Score: 0");
                            root.getChildren().add(field);
                            root.getChildren().add(labels);
                            start();
                        });


                    }
                }
            }
        };
        timer.start();


        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 20, 10, 20));
        root.getChildren().add(field);
        root.getChildren().add(labels);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e->{
            if (e.getCode() == KeyCode.UP && field.snake.head.direction != Direction.SOUTH) {
                setDirection(field.snake, Direction.NORTH);
            }

            if (e.getCode() == KeyCode.DOWN && field.snake.head.direction != Direction.NORTH) {
                setDirection(field.snake, Direction.SOUTH);
            }

            if (e.getCode() == KeyCode.LEFT && field.snake.head.direction != Direction.EAST) {
                setDirection(field.snake, Direction.WEST);
            }

            if (e.getCode() == KeyCode.RIGHT && field.snake.head.direction != Direction.WEST) {
                setDirection(field.snake, Direction.EAST);
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake game");
        primaryStage.show();
    }

    public void setDirection(Snake snake, Direction direction){
        if(!changed){
            snake.setDirection(direction);
            changed = true;
        }
    }

    public void hashResult(File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);

        Random random = new Random();

        String firstLine = "";
        for(int i = 0; i<13; i++){
            firstLine = firstLine.concat(Character.toString((char) (random.nextInt(80) + 32)));
        }
        firstLine = firstLine.concat("\n");
        writer.print(firstLine);

        String secondLine = "";
        for(int i = 0; i<23; i++){
            secondLine = secondLine.concat(Character.toString((char) (random.nextInt(80) + 32)));
        }
        secondLine = secondLine.concat("\n");
        writer.print(secondLine);

        String thirdLine = "";
        for(int i = 0; i<47; i++){
            if (i != 2 ){
                thirdLine = thirdLine.concat(Character.toString((char) (random.nextInt(80) + 32)));
            } else{
                int toCode = field.highestScore + 33;
                char toString = (char) toCode;
                String toConcatenat = Character.toString(toString);
                thirdLine = thirdLine.concat(toConcatenat);
            }
        }
        thirdLine = thirdLine.concat("\n");
        writer.print(thirdLine);

        String fourthLine = "";
        for(int i = 0; i<29; i++){
            fourthLine = fourthLine.concat(Character.toString((char) (random.nextInt(80) + 32)));
        }
        fourthLine = fourthLine.concat("\n");
        writer.print(fourthLine);


        int numberOfAdditionalLines = 19;
        String additionalLine;
        for (int i = 0; i<numberOfAdditionalLines; i++){
            int randomLength = random.nextInt(35);
            additionalLine = "";
            for(int k = 0; k<randomLength; k++){
                additionalLine = additionalLine.concat(Character.toString((char) (random.nextInt(80) + 32)));
            }
            additionalLine = additionalLine.concat("\n");
            writer.print(additionalLine);
        }

        writer.flush();
    }


    public static void main(String[] args) {
        launch(args);
    }
}