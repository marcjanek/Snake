package model;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

class Apples extends GameObject
{
    private final int MAX_APPLES = 10;
    private final int WIDTH, HEIGHT;
    private final Snake snake;
    private Random random = new Random();

    Apples(Snake snake, int WIDTH, int HEIGHT, LinkedList freePoints)
    {
        super(new Color(200, 0, 6), new Color(250, 64, 0), freePoints);
        this.snake = snake;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
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

    private void addApple(int numerOfNewApples)
    {
        for (int i = 0; i < numerOfNewApples; ++i)
            addApple();
    }
}
