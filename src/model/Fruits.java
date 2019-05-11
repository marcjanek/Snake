package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Fruits
{
    private final Random random = new Random();
    private final HashSet<Point> freePoints;
    private final HashSet<Point> fruits;
    int maxFruits = 50;

    Fruits(final HashSet<Point> freePoints)
    {
        fruits = new HashSet<>();
        this.freePoints = freePoints;
        addFruit(maxFruits);
    }

    final void add(final Point newHead)
    {
        if (!freePoints.isEmpty())
        {
            Point newFruit;
            do
            {
                newFruit = (Point) freePoints.toArray()[random.nextInt(freePoints.size())];
            } while (newFruit.equals(newHead));
            fruits.add(newFruit);
            freePoints.remove(newFruit);
        }

    }

    private void add()
    {
        final Point newFruit = (Point) freePoints.toArray()[random.nextInt(freePoints.size())];
        fruits.add(newFruit);
        freePoints.remove(newFruit);
    }

    final void remove(final Point point)
    {
        freePoints.add(point);
        fruits.remove(point);
    }

    final void reset()
    {
        fruits.clear();
        addFruit(maxFruits);
    }

    private void addFruit(final int numberOfNewApples)
    {
        for (int i = 0; i < numberOfNewApples; ++i)
            add();
    }

    @Contract(pure = true)
    final boolean contains(final Point point)
    {
        return fruits.contains(point);
    }

    @NotNull
    @Contract(" -> new")
    final Queue<Point> get()
    {
        return new LinkedList<>(fruits);
    }
}
