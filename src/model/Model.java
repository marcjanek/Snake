package model;

import enums.Direction;
import enums.States;

import java.awt.*;

public class Model
{
    private final int WIDTH = 40;
    private final int HEIGHT = 40;

    private Snake snake;
    private Apples apples;
    private States actualState;

    public Model()
    {
        snake = new Snake();
        apples = new Apples(snake, WIDTH, HEIGHT);
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
        for (int i = 1; i < snake.getSize(); ++i)
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

    int getScore()
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
}

