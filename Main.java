package SnakeGame;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;



public class Main extends Application {
    private static Pane pane;
    private static Scene gameScreen;

    @Override
    public void start(Stage primaryStage){

        pane = new Pane();
        pane.setStyle("-fx-background-color:black");
        gameScreen = new Scene(pane, 500, 500);
        Snake mainSnake =  new Snake();
        primaryStage.setScene(gameScreen);
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);
        primaryStage.show();


        mainSnake.getHead().getBodyShape().requestFocus();
    }

    static Pane getPane(){
        return pane;
    }
    static Scene  getGameScreen(){
        return gameScreen;
    }
}

