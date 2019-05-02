package model;

import java.awt.*;
import java.util.LinkedList;

class Snake extends GameObject
{
    private final int START_X;
    private final int START_Y;

    Snake(int WIDTH, int HEIGHT, LinkedList freePoints)
    {
        super(freePoints);
        START_X = WIDTH / 4;
        START_Y = HEIGHT / 2;
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

    void pollTail()
    {
        freePoints.add(linkedList.pollLast());
    }

    void addFirst(Point point)
    {
        linkedList.addFirst(point);
    }


    int score()
    {
        return linkedList.size() - 1;
    }
}