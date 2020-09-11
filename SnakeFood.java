package SnakeGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SnakeFood {
    private Rectangle food;
    private static int count = 0;

    SnakeFood(){
        food = new Rectangle(SnakeBody.BODY_SIZE, SnakeBody.BODY_SIZE);
        food.setX(8 + (11 * ((int)(Math.random() * count))));
        food.setY(8 + (11 * ((int)(Math.random() * count))));
        System.out.println("(" + food.getX() + ", " + food.getY() + ")");
        food.setFill(Color.RED);
        Main.getPane().getChildren().add(food);
    }

    public boolean ateFood(SnakeBody head){
        return (head.getX() == food.getX() && head.getY() == food.getY());
    }

    public static void initiateFood(){
        for(int i = 8; i < (Main.getGameScreen().getHeight() - SnakeBody.BODY_SIZE); i = i + 11){
            ++count;
        }
    }

    public void changePosition(){
        food.setX(8 + (11 * ((int)(Math.random() * count))));
        food.setY(8 + (11 * ((int)(Math.random() * count))));
    }



}
