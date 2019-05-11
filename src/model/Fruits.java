package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * class performing operations on fruits collection
 */
class Fruits
{
    /**
     * generator random coordinates for new fruits
     */
    private final Random random = new Random();
    /**
     * hashSet which contains all free coordinates in arena
     */
    private final HashSet<Point> freePoints;
    /**
     * HashSet holding coordinates of fruits
     */
    private final HashSet<Point> fruits;
    /**
     * maximal number of fruits on arena
     */
    int maxFruits = 50;

    /**
     * constructor creating collection of fruits
     *
     * @param freePoints reference to hashSet which contains all free coordinates in arena
     */
    Fruits(final HashSet<Point> freePoints)
    {
        fruits = new HashSet<>();
        this.freePoints = freePoints;
        addFruit(maxFruits);
    }

    /**
     * if area has place for new apple, new fruit will be added if is not equal to new head
     * @param newHead position of new head where is forbidden to place fruit
     */
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

    /**
     * method adds fruit on random free position
     */
    private void add()
    {
        final Point newFruit = (Point) freePoints.toArray()[random.nextInt(freePoints.size())];
        fruits.add(newFruit);
        freePoints.remove(newFruit);
    }

    /**
     * method remove fruit from arena and adds removed position to free
     * @param point fruit coordinates to remove from collection
     */
    final void remove(final Point point)
    {
        freePoints.add(point);
        fruits.remove(point);
    }

    /**
     * method resets all class settings to construction settings
     */
    final void reset()
    {
        fruits.clear();
        addFruit(maxFruits);
    }

    /**
     * method adding new fruits
     * @param numberOfNewApples number of new apples to add
     */
    private void addFruit(final int numberOfNewApples)
    {
        for (int i = 0; i < numberOfNewApples; ++i)
            add();
    }

    /**
     * method checks if point contains to fruit collection
     * @param point point to be checked if contains to fruit collection
     * @return true if fruit collection contains point, else false
     */
    @Contract(pure = true)
    final boolean contains(final Point point)
    {
        return fruits.contains(point);
    }

    /**
     * method returning list of fruits collection coordinates
     * @return fruits collection coordinates
     */
    @NotNull
    @Contract(" -> new")
    final Queue<Point> get()
    {
        return new LinkedList<>(fruits);
    }
}
