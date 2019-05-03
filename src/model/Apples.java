package model;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

class Apples
{
    int MAX_APPLES = 50;
    private Random random = new Random();
    private HashSet freePoints;
    private HashSet<Point> apples;

    Apples(HashSet freePoints)
    {
        apples = new HashSet<>();
        this.freePoints = freePoints;
        addApple(MAX_APPLES);
    }

    void add(Point newHead)
    {
        Point newApple;
        do
        {
            newApple = (Point) freePoints.toArray()[random.nextInt(freePoints.size())];
        } while (newApple.equals(newHead));

        apples.add(newApple);
        freePoints.remove(newApple);
    }

    void add()
    {
        Point newApple = (Point) freePoints.toArray()[random.nextInt(freePoints.size())];
        apples.add(newApple);
        freePoints.remove(newApple);
    }

    void remove(Point point)
    {
        freePoints.add(point);
        apples.remove(point);
    }

    void reset()
    {
        apples.clear();
        addApple(MAX_APPLES);
    }

    private void addApple(int numberOfNewApples)
    {
        for (int i = 0; i < numberOfNewApples; ++i)
            add();
    }

    boolean contains(Point point)
    {
        return apples.contains(point);
    }

    Point get(int index)
    {
        return (Point) apples.toArray()[index];
    }
}
