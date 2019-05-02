package model;

import enums.Direction;
import enums.Level;
import enums.States;

import java.awt.*;
import java.util.Date;
import java.util.LinkedList;

public class Model
{
    public final int WIDTH = 40;
    public final int HEIGHT = 40;
    public final int PROPORTION = 23;
    private final LinkedList<Point> freePoints;
    public GamesHistory gamesHistory;
    public long startTimeOfGame;
    public int speed = 500;
    public Direction lastDirection = Direction.RIGHT;
    private Snake snake;
    private Apples apples;
    private States actualState;


    public Model()
    {
        freePoints = new LinkedList<>();
        resetFreePoints();
        snake = new Snake(WIDTH, HEIGHT, freePoints);
        apples = new Apples(freePoints);
        gamesHistory = new GamesHistory();
        actualState = States.READY;
    }

    private void resetFreePoints()
    {
        for (int i = 0; i < WIDTH; ++i)
            for (int j = 0; j < HEIGHT; ++j)
                freePoints.add(new Point(i, j));
    }

    public int highScore()
    {
        return gamesHistory.highScore.score;
    }

    public GamesHistory.GameStatistics getHistoryData(int index)
    {
        return gamesHistory.get(index);
    }

    public int sizeHistoryData()
    {
        return gamesHistory.size();
    }

    private Point moveHead(Direction direction)
    {
        int x = snake.getHead().x;
        int y = snake.getHead().y;
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
        }
        return new Point(0, 0);
    }

    public void moveSnake(Direction direction)
    {
        Point newHead = moveHead(direction);
        if (!headOnApple(newHead))
        {
            if (collision(newHead))
            {
                gameOver();
                return;
            }
            snake.pollTail();
        }
        snake.addFirst(newHead);
    }

    private void gameOver()
    {
        setGameState(States.GAME_OVER);
        gamesHistory.add(getScore(), new Date(System.currentTimeMillis()), System.currentTimeMillis() - startTimeOfGame);
    }

    private boolean headOnApple(Point newHead)
    {
        if (apples.contains(newHead))
        {
            apples.poll(newHead);
            apples.addAppleNotIn(newHead);
            return true;
        }
        return false;
    }

    private boolean collision(Point newHead)
    {
        return snake.contains(newHead) || (snake.getHead().x < 0 || snake.getHead().x >= WIDTH || snake.getHead().y < 0 || snake.getHead().y >= HEIGHT);
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
        return snake.getSize();
    }

    public Point getApples(int index)
    {
        return apples.get(index);
    }

    public int getApplesSize()
    {
        return apples.getSize();
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
        resetFreePoints();
        snake.reset();
        apples.reset();
        lastDirection = Direction.RIGHT;
        actualState = States.READY;

    }
}

