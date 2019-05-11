package controller;

import enums.Direction;
import enums.States;
import model.Model;
import view.View;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * class controlling game states and performing actions during game states changes.
 */
public final class Controller implements Runnable
{
    private final Model model;
    private final View view;
    private Clip clip;
    private Clip gameOver;
    private boolean isMusicMuted = false;

    /**
     * Class constructor
     *
     * @param model
     * @param view
     */
    public Controller(final Model model, final View view)
    {
        this.model = model;
        this.view = view;
        view.setController(this);
        model.restart();
        new Thread(this).start();
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

    /**
     * method representing waiting for start of the game
     */
    private void ready()
    {
        while (model.actualState != States.PLAYING)
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

    /**
     * method representing playing snake
     */
    private void play()
    {
        model.collision = false;
        long prevTime, sleepTime;
        if (!isMusicMuted && clip.isOpen())
            clip.start();
        model.startTimeOfGame = prevTime = System.currentTimeMillis();
        while (model.actualState == States.PLAYING)
        {
            changeDirection(view.direction);
            model.moveSnake(model.lastDirection);
            view.paint();
            sleepTime = model.speed - System.currentTimeMillis() + prevTime;
            if (sleepTime > 0)
            {
                try
                {
                    Thread.sleep((int) sleepTime);
                    prevTime = System.currentTimeMillis();
                } catch (InterruptedException e)
                {
                    e.printStackTrace(System.out);
                    System.exit(1);
                }
            }
        }
        initMusic();
        gameOver();
    }

    /**
     * method representing game over state.
     */
    private void gameOver()
    {
        if (model.collision)
            gameOverMusic();
        while (model.actualState != States.READY)
        {
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                e.printStackTrace(System.out);
                System.exit(1);
            }
        }
        if (gameOver != null && gameOver.isRunning())
            gameOver.stop();
        ready();
    }

    /**
     * If music is loaded and unmute, play game over sound
     */
    public final void gameOverMusic()
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

    /**
     * additional thread for score bar repainting method
     */
    @Override
    public final void run()
    {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                view.paintScoreBar();
            }
        }, 0, 10);
    }

    /**
     * method change direction if it is not equal to direction in last move and if it is not equal to opposite direction in last move
     *
     * @param newDirection
     */
    private void changeDirection(final Direction newDirection)
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

    /**
     *  method playing random song from loaded
     */
    private void initMusic()
    {
        if (view.music.size() > 1)
        {
            final Random random=new Random();
            String songName;
            do
            {
                songName = (String) view.music.keySet().toArray()[random.nextInt(view.music.size())];
            } while (songName.equals("mute") || songName.equals("unmute"));

            try
            {
                if (clip.isOpen())
                    clip.close();
                clip.open(AudioSystem.getAudioInputStream(new File("src/songs/" + songName + ".wav")));
            } catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        } else
            isMusicMuted = true;
    }

    /**
     * start new song if playing game. If song is not loaded, does not play any music clip
     *
     * @param name song name without file extension
     */
    public void changeSong(final String name)
    {
        try
        {
            if (clip.isRunning())
                clip.stop();
            if (clip.isOpen())
                clip.close();
            if (isMusicMuted)
                isMusicMuted = false;
            clip.open(AudioSystem.getAudioInputStream(new File("src/songs/" + name + ".wav")));
            if (model.actualState == States.PLAYING)
                clip.start();
        } catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    /**
     * method stops music
     */
    public void muteMusic()
    {
        isMusicMuted = true;
        if (clip.isOpen() && model.actualState == States.PLAYING)
            clip.stop();
        if (gameOver.isOpen() && model.actualState == States.GAME_OVER)
            gameOver.stop();
    }

    /**
     * method starts music if game is played
     */
    public void unMuteMusic()
    {
        isMusicMuted = false;
        if (model.actualState == States.PLAYING)
            clip.start();
    }
}
