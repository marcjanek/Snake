package model;

import controller.Controller;
import enums.Direction;
import enums.Level;
import enums.States;
import model.history.DataBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.Queue;

public final class Model
{
    public final int WIDTH = 53;
    public final int HEIGHT = 26;
    private final HashSet<Point> freePoints;
    public int bestScore = 0;
    public long startTimeOfGame;
    public int speed = 500;
    public Direction lastDirection = Direction.RIGHT;
    private final Snake snake;
    private final Fruits fruits;
    public States actualState;
    private Controller controller;
    private final DataBase dataBase;
    private Level level = Level.MEDIUM;


    public Model()
    {
        dataBase=new DataBase();
        freePoints = new HashSet<>();
        initFreePoints();
        snake = new Snake(WIDTH, HEIGHT, freePoints);
        fruits = new Fruits(freePoints);
        actualState = States.READY;
    }

    public final void setController(final Controller controller)
    {
        this.controller = controller;
    }

    private void initFreePoints()
    {
        for (int i = 0; i < WIDTH; ++i)
            for (int j = 0; j < HEIGHT; ++j)
                freePoints.add(new Point(i, j));
    }

    public final Queue<String> bestScores(final int number)
    {
        return dataBase.bestScores(number);
    }

    @NotNull
    @Contract("_ -> new")
    private Point moveHead(Direction direction)
    {
        int x = snake.head().x;
        int y = snake.head().y;
        switch (direction)
        {
            case DOWN:
                return new Point(x, ++y);
            case LEFT:
                return new Point(--x, y);
            case RIGHT:
                return new Point(++x, y);
            case UP:
                return new Point(x, --y);
            default:
                return new Point(x, y);
        }
    }

    public final void moveSnake(final Direction direction)
    {
        final Point newHead = moveHead(direction);
        if (fruits.contains(newHead))
        {
            fruits.remove(newHead);
            snake.add(newHead);
            fruits.add(newHead);
        } else if (collision(newHead))
        {
            gameOver();
            controller.gameOverMusic();
        } else
        {
            snake.removeTail();
            snake.add(newHead);
        }
    }

    public final Queue<Point> getFruits()
    {
        return fruits.get();
    }

    private void gameOver()
    {
        actualState = States.GAME_OVER;
        bestScore = Math.max(getScore(), bestScore);
        long currentTimeMillis = System.currentTimeMillis();
        dataBase.add(getScore(),
                currentTimeMillis,
                (currentTimeMillis - startTimeOfGame) / 1000,
                level.toString());
    }

    private boolean collision(final Point newHead)
    {
        final Point head = snake.head();
        return snake.contains(newHead) || (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT);
    }

    public final int getScore()
    {
        return snake.size() - 1;
    }

    public final Queue<Point> getSnake()
    {
        return snake.get();
    }

    private void setLevelSettings(Level level)
    {
        this.level = level;
        switch (level)
        {
            case NOOB:
                speed = 500;
                fruits.maxFruits = 50;
                break;
            case MEDIUM:
                speed = 200;
                fruits.maxFruits = 20;
                break;
            case HARD:
                speed = 100;
                fruits.maxFruits = 10;
                break;
            case EXPERT:
                speed = 50;
                fruits.maxFruits = 1;
                break;
        }
    }

    public final void restart()
    {
        restart(level);
    }

    public final void restart(Level level)
    {
        setLevelSettings(level);
        initFreePoints();
        snake.reset();
        fruits.reset();
        lastDirection = Direction.RIGHT;
        actualState = States.READY;
    }
}

