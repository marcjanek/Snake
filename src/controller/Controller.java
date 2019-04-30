package controller;

import View.View;
import enums.Direction;
import enums.States;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Controller
{
    private final Model model;
    private final View view;
    private static final int TIMER_DELAY = 80;
    public javax.swing.Timer timer;
    private Direction lastDirection;


    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        lastDirection = Direction.RIGHT;
        timer = new javax.swing.Timer(TIMER_DELAY, new TimerListener());
        timer.start();
    }

    private void restartGame()
    {
        model.resetGame();
        lastDirection = Direction.RIGHT;
    }

    private void changeDirection(Direction newDirection)
    {
        if (lastDirection == newDirection)
            return;
        switch (newDirection)
        {
            case UP:
                if (lastDirection != Direction.DOWN) lastDirection = newDirection;
                break;
            case DOWN:
                if (lastDirection != Direction.UP) lastDirection = newDirection;
                break;
            case LEFT:
                if (lastDirection != Direction.RIGHT) lastDirection = newDirection;
                break;
            case RIGHT:
                if (lastDirection != Direction.LEFT) lastDirection = newDirection;
                break;
        }
    }

    private class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (model.getGameState() == States.PLAYING)
            {
                changeDirection(view.direction);
                model.moveSnake(lastDirection);
                view.arena.repaint();
                view.score.updateScore();
                view.jFrame.repaint();
                try
                {
                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_0);
                } catch (AWTException e1)
                {
                }


            }
        }

    }

}
