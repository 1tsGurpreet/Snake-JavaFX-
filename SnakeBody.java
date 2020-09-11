package SnakeGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class SnakeBody {
    private Rectangle bodyShape;
    public static final int BODY_SIZE = 10;

    SnakeBody(){
        System.out.println("Don't declare without initializing");
    }

    SnakeBody(double x, double y){
        bodyShape =  new Rectangle(x, y, BODY_SIZE, BODY_SIZE);
        bodyShape.setFill(Color.GREEN);
    }

    public Rectangle getBodyShape(){
        return bodyShape;
    }

    public double getX(){
        return bodyShape.getX();
    }
    public double getY(){
        return bodyShape.getY();
    }
    public void setX(double x){
        bodyShape.setX(x);
    }
    public void setY(double y){
        bodyShape.setY(y);
    }

    public SnakeBody appendSnake(SnakeBody snake) {
        SnakeBody tmp = new SnakeBody(snake.getX() - BODY_SIZE - 1, snake.getY());
        return tmp;
    }
}
