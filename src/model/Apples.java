package model;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

class Apples extends GameObject
{
    int MAX_APPLES = 50;
    private Random random = new Random();

    Apples(LinkedList freePoints)
    {
        super(freePoints);
        addApple(MAX_APPLES);
    }

    void addAppleNotIn(Point newHead)
    {
        Point newApple;
        do
        {
            newApple = freePoints.get(random.nextInt(freePoints.size()));
        } while (newApple.equals(newHead));
        add(newApple);
        freePoints.remove(newApple);
    }

    void addApple()
    {
        add(freePoints.remove(random.nextInt(freePoints.size())));
    }

    void poll(Point point)
    {
        linkedList.remove(point);
    }

    void reset()
    {
        clear();
        addApple(MAX_APPLES);
    }

    private void addApple(int numberOfNewApples)
    {
        for (int i = 0; i < numberOfNewApples; ++i)
            addApple();
    }
}
