package model;


import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * class representing snake body
 */
final class Snake {
    /**
     * constraint holding start x-axis position
     */
    private final int START_X;
    /**
     * constraint holding start y-axis position
     */
    private final int START_Y;
    /**
     * Linked list holding coordinates of snake body where on the beginning is snake's head
     */

    @NotNull
    private final LinkedList<Point> snakeBody;
    /**
     * hashSet which contains all free coordinates in arena
     */
    private final HashSet<Point> freePoints;

    /**
     * initialize snake body list and puts snake's head on start coordinates
     *
     * @param WIDTH      game arena width
     * @param HEIGHT     game arena height
     * @param freePoints reference to hashSet which contains all free coordinates in arena
     */
    Snake(final int WIDTH, final int HEIGHT, final HashSet<Point> freePoints) {
        snakeBody = new LinkedList<>();
        this.freePoints = freePoints;
        START_X = WIDTH / 4;
        START_Y = HEIGHT / 2;
        add(new Point(START_X, START_Y));
    }

    /**
     * returns list of snake body coordinates
     *
     * @return snake body list
     */

    @NotNull
    final Queue<Point> get() {
        return new LinkedList<>(snakeBody);
    }

    /**
     * returns snake head coordinates
     *
     * @return snake head coordinates
     */
    final Point head() {
        return snakeBody.getFirst();
    }

    /**
     * resets all class settings to construction settings
     */
    final void reset() {
        snakeBody.clear();
        add(new Point(START_X, START_Y));
    }

    /**
     * polls snake tail
     */
    final void removeTail() {
        freePoints.add(snakeBody.pollLast());
    }

    /**
     * adds point to snake body
     *
     * @param point coordinates to be added to snake body
     */
    final void add(final Point point) {
        freePoints.remove(point);
        snakeBody.addFirst(point);
    }

    /**
     * calculates size of snake
     *
     * @return length of snake
     */
    final int size() {
        return snakeBody.size();
    }

    /**
     * checks if point contains to snake body
     *
     * @param point point to be checked if contains to snake body
     * @return true if snake body contains point, else false
     */
    final boolean contains(final Point point) {
        return snakeBody.contains(point);
    }
}