package model;

import controller.Controller;
import enums.Direction;
import enums.Level;
import enums.States;

import java.awt.*;
import java.util.HashSet;

public class Model
{
    public final int WIDTH = 10;
    public final int HEIGHT = 10;
    public final int PROPORTION = 23;
    private final HashSet<Point> freePoints;
    public int bestScore = 0;
    public long startTimeOfGame;
    public int speed = 500;
    public Direction lastDirection = Direction.RIGHT;
    private Snake snake;
    private Apples apples;
    private States actualState;
    private Controller controller;


    public Model()
    {
        freePoints = new HashSet<>();
        initFreePoints();
        snake = new Snake(WIDTH, HEIGHT, freePoints);
        apples = new Apples(freePoints);
        actualState = States.READY;
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    private void initFreePoints()
    {
        for (int i = 0; i < WIDTH; ++i)
            for (int j = 0; j < HEIGHT; ++j)
                freePoints.add(new Point(i, j));
    }

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
                return new Point(0, 0);

        }
    }

    public void moveSnake(Direction direction)
    {
        Point newHead = moveHead(direction);
        if (apples.contains(newHead))
        {
            apples.remove(newHead);
            snake.add(newHead);
            apples.add(newHead);
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

    private void gameOver()
    {
        setGameState(States.GAME_OVER);
        bestScore = Math.max(getScore(), bestScore);
    }

    private boolean collision(Point newHead)
    {
        return snake.contains(newHead) || (snake.head().x < 0 || snake.head().x >= WIDTH || snake.head().y < 0 || snake.head().y >= HEIGHT);
    }

    public int getScore()
    {
        return snake.score();
    }

    public States getGameState()
    {
        return actualState;
    }

    public void setGameState(States newState)
    {
        this.actualState = newState;
    }

    public Point getSnake(int index)
    {
        return snake.get(index);
    }

    public int getSnakeSize()
    {
        return snake.size();
    }

    public Point getApple(int index)
    {
        return apples.get(index);
    }

    public int getApplesSize()
    {
        return apples.MAX_APPLES;
    }

    private void setLevelSettings(Level level)
    {
        switch (level)
        {
            case EASY:
                speed = 500;
                apples.MAX_APPLES = 50;
                break;
            case MEDIUM:
                speed = 200;
                apples.MAX_APPLES = 20;
                break;
            case HARD:
                speed = 100;
                apples.MAX_APPLES = 10;
                break;
            case EXPERT:
                speed = 50;
                apples.MAX_APPLES = 1;
                break;
        }
    }

    public void restart(Level level)
    {
        setLevelSettings(level);
        initFreePoints();
        snake.reset();
        apples.reset();
        lastDirection = Direction.RIGHT;
        actualState = States.READY;
    }
}

