package controller;

import enums.Direction;
import enums.Level;
import enums.States;
import model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import view.View;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * class controlling game states and performing actions during game states changes.
 */
public final class Controller implements Runnable
{
    /**
     * reference to class Model
     */
    @NotNull
    private final Model model;
    /**
     * reference to class View
     */
    private View view;
    /**
     * background song
     */
    private volatile Clip clip;
    /**
     * game over sound
     */
    private Clip gameOver;
    /**
     * true if music is muted, otherwise false
     */
    private volatile boolean isMusicMuted = false;
    /**
     * signalizes desire of restarting the game
     */
    private volatile boolean restart = false;
    /**
     * new level of game difficult
     */
    @Nullable
    private Level restartLevel;

    /**
     * @param model reference to class model
     */
    public Controller(@NotNull final Model model)
    {
        this.model = model;
        model.restart();
        try
        {
            gameOver = AudioSystem.getClip();
            clip = AudioSystem.getClip();
        } catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public final void start(@NotNull final View view)
    {
        this.view = view;
        initMusic();
        new Thread(this).start();
        ready();
    }

    /**
     * represents waiting for start of the game
     */
    private void ready()
    {
        while (model.actualState != States.PLAYING)
        {
            tryRestart();
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
     * represents playing snake
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
            tryRestart();
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
     * represents game over state
     */
    private void gameOver()
    {
        if (model.collision)
            gameOverMusic();
        while (model.actualState != States.READY)
        {
            tryRestart();
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
     * If musicMap is loaded and unmute, play game over sound
     */
    private void gameOverMusic()
    {
        if (!isMusicMuted)
        {
            try
            {
                final String path = "songs/Super Mario Game Over.wav";
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
                InputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(inputStream));
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
                gameOver = AudioSystem.getClip();
                gameOver.open(audioInputStream);
            } catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
            gameOver.start();
        }
    }

    /**
     * sets flag to signalize desire of restarting of game
     */
    public final void setRestart()
    {
        restart = true;
    }

    /**
     * sets flag to signalize desire of restarting of game
     *
     * @param level new level to restart the game
     */
    public final void setRestart(final Level level)
    {
        restart = true;
        restartLevel = level;
    }

    /**
     * checks if view signalized restarting game
     */
    private void tryRestart()
    {
        if (restart)
        {
            restart = false;
            if (restartLevel == null)
                model.restart();
            else
            {
                model.restart(restartLevel);
                restartLevel = null;
            }
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
     * @param newDirection last pressed direction on keyboard
     */
    private void changeDirection(@NotNull final Direction newDirection)
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
     *  plays random song from loaded
     */
    private void initMusic()
    {
        if (view.musicMap.size() > 1)
        {
            final Random random=new Random();
            String songName;
            do
            {
                songName = (String) view.musicMap.keySet().toArray()[random.nextInt(view.musicMap.size())];
            } while (songName.equals("mute") || songName.equals("unmute"));

            try
            {
                if (clip.isOpen())
                    clip.close();
                final String path = "songs/" + songName + ".wav";
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
                InputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(inputStream));
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
                clip.open(audioInputStream);
            } catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        } else
            isMusicMuted = true;
    }

    /**
     * starts new song if playing game. If song is not loaded, does not play any musicMap clip
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

            final String path = "songs/" + name + ".wav";
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(Objects.requireNonNull(inputStream));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip.open(audioInputStream);
            if (model.actualState == States.PLAYING)
                clip.start();
        } catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    /**
     * stops musicMap
     */
    public void muteMusic()
    {
        isMusicMuted = true;
        if (clip.isOpen() && model.actualState == States.PLAYING)
            clip.stop();
        if (gameOver.isOpen() && (model.actualState == States.GAME_OVER))
            gameOver.stop();
    }

    /**
     * starts musicMap if game is played
     */
    public void unMuteMusic()
    {
        isMusicMuted = false;
        if (model.actualState == States.PLAYING)
            clip.start();
    }
}
