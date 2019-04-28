package controller;

import enums.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeysControl implements KeyListener
{
    Direction direction;

    public KeysControl()
    {
        direction = Direction.RIGHT;
    }

    @Override
    public void keyPressed(KeyEvent evt) {}

    @Override
    public void keyReleased(KeyEvent evt) {}

    @Override
    public void keyTyped(KeyEvent evt) {}

    void changeDirection(Direction newDirection)
    {
        if (direction == newDirection)
            return;
        switch (newDirection)
        {
            case UP:
                if (direction != Direction.DOWN) direction = newDirection;
                break;
            case DOWN:
                if (direction != Direction.UP) direction = newDirection;
                break;
            case LEFT:
                if (direction != Direction.RIGHT) direction = newDirection;
                break;
            case RIGHT:
                if (direction != Direction.LEFT) direction = newDirection;
                break;
        }
    }
}
