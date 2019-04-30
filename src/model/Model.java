package model;

import enums.Direction;
import enums.States;

import java.awt.*;
import java.util.LinkedList;

public class Model
{
    public final int WIDTH = 40;
    public final int HEIGHT = 40;
    public final int PROPRTION = 24;

    private Snake snake;
    private Apples apples;
    private States actualState;
    private GamesHistory gamesHistory;
    private final LinkedList<Point> freePoints;
    public Model()
    {
        freePoints = new LinkedList<>();
        for (int i = 0; i < WIDTH; ++i)
        {
            for (int j = 0; j < HEIGHT; ++j)
            {
                freePoints.add(new Point(i, j));
            }
        }
        snake = new Snake(WIDTH, HEIGHT, freePoints);
        apples = new Apples(snake, WIDTH, HEIGHT, freePoints);
        gamesHistory = new GamesHistory();
        actualState = States.PLAYING;
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
                resetGame();
                return;
            }
            snake.pollTail();
        }
        snake.addFirst(newHead);
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

    void setGameState(States newState)
    {
        this.actualState = newState;
    }

    void removeFood(Point appleToRemove)
    {
        apples.poll(appleToRemove);
    }

    public void resetGame()
    {
        snake.reset();
        apples.reset();
        actualState = States.SCORES;
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

    public Color getSnakeBorderColor()
    {
        return snake.borderColor;
    }

    public Color getSnakeBackgroundColor()
    {
        return snake.backgroundColor;
    }

    public Color getAppleBorderColor()
    {
        return apples.borderColor;
    }

    public Color getAppleBackgroundColor()
    {
        return apples.backgroundColor;
    }
}

