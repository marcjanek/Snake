package controller;

import View.View;
import enums.Direction;
import enums.States;
import model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Controller implements Runnable
{
    private final Model model;
    private final View view;
    private Direction direction;
    private Thread thread;

    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        direction = Direction.RIGHT;
        thread = new Thread(this, "Snake");
        thread.start();
    }

    public void run()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if (model.getGameState() == States.PLAYING)
                {
                    model.moveSnake(direction);
                    //TODO:VIEW PRINT
                }
            }
        }, 0, 1000);
    }

    private void restartGame()
    {
        model.resetGame();
        direction = Direction.RIGHT;
    }

    private void changeDirection(Direction newDirection)
    {
        if (model.getGameState() == States.PLAYING)
        {
            if (direction == newDirection) return;
            switch (newDirection)
            {
                case UP:
                    if (direction != Direction.DOWN) direction = newDirection;
                    break;
                case DOWN:
                    if (direction != Direction.UP) direction = newDirection;
                    break;
                case LEFT:
                    if (direction != Direction.RIGHT) direction = newDirection;
                    break;
                case RIGHT:
                    if (direction != Direction.LEFT) direction = newDirection;
                    break;
            }
        }
    }
    //TODO control up,down,left,right
}
