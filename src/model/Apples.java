package model;

import java.awt.*;
import java.util.Random;

class Apples extends GameObject
{
    private final int MAX_APPLES = 1;
    private final int WIDTH, HEIGHT;
    private final Snake snake;
    private Random random = new Random();

    Apples(Snake snake, int WIDTH, int HEIGHT)
    {
        super();
        this.snake = snake;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        addApple(MAX_APPLES);
    }

    void addApple()
    {
        Point newApple = new Point();
        do
        {
            newApple.setLocation(random.nextInt(WIDTH), random.nextInt(HEIGHT));
        } while (snake.contains(newApple) || contains(newApple));
        add(newApple);
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
