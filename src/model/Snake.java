package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

class Snake
{
    private final int START_X;
    private final int START_Y;
    ArrayList<Point> snakeBody;
    HashSet<Point> freePoints;

    Snake(int WIDTH, int HEIGHT, HashSet freePoints)
    {
        snakeBody = new ArrayList<>();
        this.freePoints = freePoints;

        START_X = WIDTH / 4;
        START_Y = HEIGHT / 2;
        add(new Point(START_X, START_Y));
    }

    Point get(int index)
    {
        return snakeBody.get(index);
    }

    Point head()
    {
        return snakeBody.get(0);
    }
    void reset()
    {
        snakeBody.clear();
        snakeBody.add(0, new Point(START_X, START_Y));
        freePoints.remove(new Point(START_X, START_Y));
    }

    void removeTail()
    {
        freePoints.add(snakeBody.remove(snakeBody.size() - 1));
    }

    void add(Point point)
    {
        freePoints.remove(point);
        snakeBody.add(0, point);
    }
    int score()
    {
        return snakeBody.size() - 1;
    }

    int size()
    {
        return snakeBody.size();
    }

    boolean contains(Point point)
    {
        return snakeBody.contains(point);
    }
}