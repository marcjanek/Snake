package model;

import java.awt.*;

class Snake extends GameObject
{
    private static final int START_X = 10;
    private static final int START_Y = 10;

    Snake()
    {
        super(new Color(100, 100, 100), new Color(150, 150, 150));
        add(new Point(START_X, START_Y));
    }

    void reset()
    {
        clear();
        add(new Point(START_X, START_Y));
    }

    Point getHead()
    {
        return linkedList.getFirst();
    }

    Point getTail()
    {
        return linkedList.getLast();
    }

    void pollHead()
    {
        linkedList.pollFirst();
    }

    void pollTail()
    {
        linkedList.pollLast();
    }

    void addFirst(Point point)
    {
        linkedList.addFirst(point);
    }

    void addLast(Point point)
    {
        linkedList.addLast(point);
    }

    void moveSnake(Point newHeadCoordinates)
    {
        pollTail();
        addFirst(newHeadCoordinates);
    }

    int score()
    {
        return linkedList.size() - 1;
    }
}