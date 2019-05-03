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

public class Controller implements Runnable
{
    private final Model model;
    private final View view;
    private double prevTime;
    private double presTime;
    private double sleepTime;


    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        new Thread(this::run).start();
        ready();
    }

    private void ready()
    {
        while (model.getGameState() != States.PLAYING)
        {
            view.paint();
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                System.exit(1);
            }
        }
        play();
    }

    private void play()
    {
        prevTime = System.currentTimeMillis();
        while (model.getGameState() == States.PLAYING)
        {

            changeDirection(view.direction);
            model.moveSnake(model.lastDirection);
            view.paint();
            sleepTime = model.speed - (System.currentTimeMillis() - prevTime);
            if (sleepTime > 0)
            {
                try
                {
                    Thread.sleep((int) sleepTime);
                    prevTime = System.currentTimeMillis();
                } catch (InterruptedException e)
                {
                    System.exit(1);
                }
            }

        }
        gameOver();
    }

    private void gameOver()
    {
        while (model.getGameState() != States.READY)
        {
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                System.exit(1);
            }
        }
        ready();
    }

    @Override
    public void run()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                view.paintScoreBar();
            }
        }, 0, 1000);
    }

    private void changeDirection(Direction newDirection)
    {
        if (model.lastDirection != newDirection)
            switch (newDirection)
            {
                case UP:
                    if (model.lastDirection != Direction.DOWN) model.lastDirection = newDirection;
                    break;
                case DOWN:
                    if (model.lastDirection != Direction.UP) model.lastDirection = newDirection;
                    break;
                case LEFT:
                    if (model.lastDirection != Direction.RIGHT) model.lastDirection = newDirection;
                    break;
                case RIGHT:
                    if (model.lastDirection != Direction.LEFT) model.lastDirection = newDirection;
                    break;
            }
    }
}
