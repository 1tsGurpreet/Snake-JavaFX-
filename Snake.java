package SnakeGame;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import static SnakeGame.SnakeBody.BODY_SIZE;


public class Snake {
    private final int START_SIZE = 5;
    private final double ACCELRATION = 10;
    private double time = 70;
    private ArrayList<SnakeBody> snake;
    private SnakeBody head;
    private SnakeFood food;
    private SnakeBody tail;
    private Timeline movement;
    private Timeline up;
    private Timeline down;
    private Timeline left;
    private Timeline right;

    Snake(){
        snake = new ArrayList<SnakeBody>();
        snake.add(new SnakeBody(Main.getGameScreen().getWidth()/2,Main.getGameScreen().getHeight()/2));
        head = snake.get(0);
        Main.getPane().getChildren().add(snake.get(0).getBodyShape());
        tail = snake.get(0);

        for(int i = 0; i < START_SIZE - 1; ++i){
            snakeGrow("RIGHT");
        }

        SnakeFood.initiateFood();
        food = new SnakeFood();

        Line lftBorder = new Line(5, 5, 5, Main.getGameScreen().getHeight() - 5);
        Line topBorder = new Line(5, 5, Main.getGameScreen().getWidth() - 5, 5);
        Line bottomBorder = new Line(5, Main.getGameScreen().getHeight() - 5, Main.getGameScreen().getWidth() -5,Main.getGameScreen().getHeight()-5 );
        Line rightBorder = new Line(Main.getGameScreen().getWidth() - 5, 5, Main.getGameScreen().getWidth() - 5, Main.getGameScreen().getHeight() - 5);
        lftBorder.setStroke(Color.RED);
        topBorder.setStroke(Color.RED);
        bottomBorder.setStroke(Color.RED);
        rightBorder.setStroke(Color.RED);
        Main.getPane().getChildren().addAll(lftBorder, topBorder, bottomBorder, rightBorder);

        down = new Timeline(new KeyFrame(Duration.millis(time), event ->{
            if(snakeInRange(head.getX(), head.getY()) || snakeOverlap()) gameOver();
            if(food.ateFood(head)){
                snakeGrow("DOWN");
                food.changePosition();
                System.out.print(time);
            }
            double tmpX = getHead().getX();
            double tmpY = getHead().getY();
            head.setY(head.getY() + ACCELRATION + 1);   // + 1 is for the space in between snakes
            moveRest(snake, tmpX, tmpY);
        }));
        up = new Timeline(new KeyFrame(Duration.millis(time), event ->{
            if(snakeInRange(head.getX(), head.getY()) || snakeOverlap()) gameOver();
            if(food.ateFood(head)){
                snakeGrow("UP");
                food.changePosition();
            }
            double tmpX = getHead().getX();
            double tmpY = getHead().getY();
            snake.get(0).setY( snake.get(0).getY()  - ACCELRATION - 1);
            moveRest(snake, tmpX, tmpY);
        }));
        right = new Timeline(new KeyFrame(Duration.millis(time), event ->{
            if(snakeInRange(head.getX(), head.getY()) || snakeOverlap()) gameOver();
            if(food.ateFood(head)){
                snakeGrow("RIGHT");
                food.changePosition();
            }
            double tmpX =  getHead().getX();
            double tmpY =  getHead().getY();
            snake.get(0).setX( snake.get(0).getX() + ACCELRATION + 1);
            moveRest(snake, tmpX, tmpY);

        }));
        left = new Timeline(new KeyFrame(Duration.millis(time), event ->{
            if(snakeInRange(head.getX(), head.getY()) || snakeOverlap()) gameOver();
            if(food.ateFood(head)){
                snakeGrow("LEFT");
                food.changePosition();
            }
            double tmpX = getHead().getX();
            double tmpY = getHead().getY();
            snake.get(0).setX( snake.get(0).getX() - ACCELRATION - 1);
            moveRest(snake, tmpX, tmpY);

        }));

        (snake.get(0)).getBodyShape().setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(movement!= null) movement.pause();
                switch(event.getCode()){
                    case S:
                    case DOWN:
                        if(movement == up){
                            movement.play();
                            break;
                        }
                        moveDown();
                        break;
                    case W:
                    case UP:
                        if(movement == down){
                            movement.play();
                            break;
                        }
                        moveUp();
                        break;
                    case A:
                    case LEFT:
                        if(movement == right){
                            movement.play();
                            break;
                        }
                        moveLeft();
                        break;
                    case D:
                    case RIGHT:
                        if(movement == left){
                            movement.play();
                            break;
                        }
                        moveRight();
                        break;

                }
            }
        });
    }


    public void moveDown(){
        movement = down;
        movement.setCycleCount(Timeline.INDEFINITE);
        movement.play();
    }

    public void moveUp(){
        movement = up;
        movement.setCycleCount(Timeline.INDEFINITE);
        movement.play();
    }

    public void moveRight(){
        movement = right;
        movement.setCycleCount(Timeline.INDEFINITE);
        movement.play();
    }

    public void moveLeft(){
        movement = left;
        movement.setCycleCount(Timeline.INDEFINITE);
        movement.play();
    }

    // common code that was being used in all moving functions
    // move the rest of the snake according to where the body piece ahead went
    public void moveRest(ArrayList<SnakeBody> snake, double tmpX, double tmpY){
        for(int i = 1; i < snake.size(); ++i){
            double xPlace = snake.get(i).getX();
            double yPlace = snake.get(i).getY();
            snake.get(i).setX(tmpX);
            snake.get(i).setY(tmpY);
            tmpX = xPlace;
            tmpY = yPlace;
        }
    }

    SnakeBody getHead(){
        return snake.get(0);
    }

    public void gameOver(){
        System.exit(1);
    }

    public void snakeGrow(){
        SnakeBody tmp =  new SnakeBody(tail.getX() -  BODY_SIZE -  1, tail.getY());
        tail =  tmp;
        snake.add(tail);
        Main.getPane().getChildren().add(tail.getBodyShape());
    }

    public void snakeGrow(String direction){
        SnakeBody tmp;
        int deltaX = 0;
        int deltaY = 0;

        switch(direction){
            case "RIGHT":
                deltaX = - BODY_SIZE - 1;
                tmp =  new SnakeBody(tail.getX() -  BODY_SIZE -  1, tail.getY());
                break;
            case "LEFT":
                deltaX = BODY_SIZE + 1;
                tmp =  new SnakeBody(tail.getX() -  BODY_SIZE -  1, tail.getY());
                break;
            case "UP":
                deltaY = BODY_SIZE + 1;
                break;
            case "DOWN":
                deltaY = - BODY_SIZE - 1;
                break;

        }

        tmp = new SnakeBody(tail.getX() + deltaX, tail.getY() +  deltaY);
        tail = tmp;
        snake.add(tail);
        Main.getPane().getChildren().add(tail.getBodyShape());

    }

    public static boolean snakeInRange(double x , double y){
        return (x < BODY_SIZE - 2 || x >= Main.getGameScreen().getWidth() - BODY_SIZE || y < BODY_SIZE - 2 || y >= Main.getGameScreen().getHeight() - BODY_SIZE);
    }

    private boolean snakeOverlap(){
        for(int i = 1; i < snake.size();  ++i){
            if(head.getX() == snake.get(i).getX() && head.getY()  == snake.get(i).getY()) return true;
        }
        return false;
    }
}
