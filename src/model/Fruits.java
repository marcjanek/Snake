package model;


import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * class performing operations on fruits collection
 */
final class Fruits {
    /**
     * generator random coordinates for new fruits
     */
    @NotNull
    private final Random random = new Random();
    /**
     * hashSet which contains all free coordinates in arena
     */
    private final HashSet<Point> freePoints;
    /**
     * HashSet holding coordinates of fruits
     */

    @NotNull
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
    Fruits(final HashSet<Point> freePoints) {
        fruits = new HashSet<>();
        this.freePoints = freePoints;
        addFruit(maxFruits);
    }

    /**
     * if area has place for new apple, new fruit will be added on free place different to new snake's head
     *
     * @param newHead position of new head where is forbidden to place fruit
     */
    final void add(final Point newHead) {
        if (!freePoints.isEmpty()) {
            Point newFruit;
            do {
                newFruit = (Point) freePoints.toArray()[random.nextInt(freePoints.size())];
            } while (newFruit.equals(newHead));
            fruits.add(newFruit);
            freePoints.remove(newFruit);
        }

    }

    /**
     * adds one fruit on random free position
     */
    private void add() {
        final Point newFruit = (Point) freePoints.toArray()[random.nextInt(freePoints.size())];
        fruits.add(newFruit);
        freePoints.remove(newFruit);
    }

    /**
     * remove fruit from arena and adds removed position to free positions
     *
     * @param point fruit coordinates to remove from collection
     */
    final void remove(final Point point) {
        freePoints.add(point);
        fruits.remove(point);
    }

    /**
     * resets all class settings to construction settings
     */
    final void reset() {
        fruits.clear();
        addFruit(maxFruits);
    }

    /**
     * adds new fruits
     *
     * @param numberOfNewApples number of new apples to add
     */
    private void addFruit(final int numberOfNewApples) {
        for (int i = 0; i < numberOfNewApples; ++i)
            add();
    }

    /**
     * return true if point contains to fruit collection, otherwise false
     *
     * @param point point to be checked if contains to fruit collection
     * @return true if fruit collection contains point, else false
     */
    final boolean contains(final Point point) {
        return fruits.contains(point);
    }

    /**
     * returning list of fruits collection coordinates
     *
     * @return fruits collection coordinates
     */

    @NotNull
    final Queue<Point> get() {
        return new LinkedList<>(fruits);
    }
}
