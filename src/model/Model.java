package model;

import enums.Direction;
import enums.States;

import java.awt.*;

public class Model
{
    public final int WIDTH = 40;
    public final int HEIGHT = 40;
    public final int PROPRTION = 24;

    private Snake snake;
    private Apples apples;
    private States actualState;
    private GamesHistory gamesHistory;

    public Model()
    {
        snake = new Snake();
        apples = new Apples(snake, WIDTH, HEIGHT);
        gamesHistory = new GamesHistory();
        actualState = States.PLAYING;
    }

    private void moveHead(Direction direction)
    {
        switch (direction)
        {
            case DOWN:
                snake.addFirst(new Point(snake.getHead().x, --snake.getHead().y));
                break;
            case LEFT:
                snake.addFirst(new Point(--snake.getHead().x, snake.getHead().y));
                break;
            case RIGHT:
                snake.addFirst(new Point(++snake.getHead().x, snake.getHead().y));
                break;
            case UP:
                snake.addFirst(new Point(snake.getHead().x, ++snake.getHead().y));
                break;

        }
    }

    public void moveSnake(Direction direction)
    {
        moveHead(direction);
        if (!Collision() && !HeadOnApple())
            snake.pollTail();
    }

    private boolean HeadOnApple()
    {
        if (apples.contains(snake.getHead()))
        {
            apples.poll(snake.getHead());
            apples.addApple();
            return true;
        }
        return false;
    }

    private boolean Collision()
    {
        boolean collision = false;
        for (int i = 2; i < snake.getSize(); ++i)
        {
            if (snake.getHead().equals(snake.get(i)))
            {
                collision = true;
                break;
            }
        }
        if (!collision && (snake.getHead().x < 0 || snake.getHead().x >= WIDTH || snake.getHead().y < 0 || snake.getHead().y > HEIGHT))
            collision = true;
        if (collision)
        {
            resetGame();
        }
        return collision;
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

