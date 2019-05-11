package controller;

import View.View;
import model.history.DataBase;
import enums.Direction;
import enums.States;
import model.Model;

import javax.sound.sampled.*;
import java.io.File;
import java.util.*;
import java.util.Timer;

public class Controller implements Runnable
{
    private final Model model;
    private final View view;
    private double prevTime;
    private double sleepTime;
    Clip clip;
    Clip gameOver;
    Random random = new Random();
    private boolean isMusicMuted = false;


    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        model.setController(this);
        view.setController(this);
        new Thread(this::run).start();
        try
        {
            clip = AudioSystem.getClip();
        } catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
        initMusic();
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
        if (!isMusicMuted && clip.isOpen())
        {
            clip.start();
        }
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
        initMusic();
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

    public void gameOverMusic()
    {
        if (!isMusicMuted)
        {
            try
            {
                gameOver = AudioSystem.getClip();
                gameOver.open(AudioSystem.getAudioInputStream(new File("src/songs/" + "Super Mario Game Over" + ".wav")));
            } catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
            gameOver.start();
        }

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
        }, 0, 10);
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

    private void initMusic()
    {
        if (view.music.size() > 2)
        {
            String songName;
            do
            {
                songName = (String) view.music.keySet().toArray()[random.nextInt(view.music.size())];
            } while (songName.equals("mute") || songName.equals("unmute"));
            musicOpen(songName);
        } else
            isMusicMuted = true;
    }

    private void musicOpen(String name)
    {

        try
        {
            if (clip.isOpen())
                clip.close();
            clip.open(AudioSystem.getAudioInputStream(new File("src/songs/" + name + ".wav")));
        } catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }

    }

    public void changeSong(String name)
    {
        clip.stop();
        try
        {
            if (clip.isRunning())
                clip.stop();
            if (clip.isOpen())
                clip.close();
            if (isMusicMuted)
                isMusicMuted = false;
            clip.open(AudioSystem.getAudioInputStream(new File("src/songs/" + name + ".wav")));
            if (model.getGameState() == States.PLAYING)
                clip.start();
        } catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public void muteMusic()
    {
        isMusicMuted = true;
        if (clip.isOpen() && (model.getGameState() == States.PLAYING||model.getGameState()==States.GAME_OVER))
            clip.stop();
    }

    public void unMuteMusic()
    {
        isMusicMuted = false;
        if (!clip.isRunning() && (model.getGameState() == States.PLAYING||model.getGameState()==States.GAME_OVER))
            clip.start();
    }
}
