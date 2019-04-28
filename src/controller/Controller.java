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
    private Thread thread;
    private final KeysControl keysControl;
    private Timer timer;

    public Controller(Model model, View view, KeysControl keysControl)
    {
        this.model = model;
        this.view = view;
        this.keysControl = keysControl;
        keysControl.direction = Direction.RIGHT;
        thread = new Thread(this, "Snake");
        thread.start();
    }
    public void run()
    {
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                if (model.getGameState() == States.PLAYING)
                {
                    model.moveSnake(keysControl.direction);
                    view.arena.repaint();
                    view.score.updateScore();
                }
            }
        }, 0, 500);
    }

    private void restartGame()
    {
        model.resetGame();
        keysControl.direction = Direction.RIGHT;
    }


}
