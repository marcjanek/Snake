package model;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * class representing snake body
 */
final class Snake
{
    /**
     *
     */
    private final int START_X;
    private final int START_Y;
    private final LinkedList<Point> snakeBody;
    private final HashSet<Point> freePoints;

    /**
     * @param WIDTH      game arena width
     * @param HEIGHT     game arena height
     * @param freePoints reference to hashSet which contains all free coordinates in arena
     */
    Snake(final int WIDTH, final int HEIGHT, final HashSet<Point> freePoints)
    {
        snakeBody = new LinkedList<>();
        this.freePoints = freePoints;
        START_X = WIDTH / 4;
        START_Y = HEIGHT / 2;
        add(new Point(START_X, START_Y));
    }

    /**
     * method returning snake list
     *
     * @return snake body list
     */
    final Queue<Point> get()
    {
        return new LinkedList<>(snakeBody);
    }

    /**
     * method returning snake head coordinates
     *
     * @return snake head coordinates
     */
    final Point head()
    {
        return snakeBody.getFirst();
    }

    /**
     * resets all class settings
     */
    final void reset()
    {
        snakeBody.clear();
        add(new Point(START_X, START_Y));
    }

    /**
     * method poll snake tail and adds it to freePoints hashSet
     */
    final void removeTail()
    {
        freePoints.add(snakeBody.pollLast());
    }

    /**
     * adds point to snake body and remove from freePoints hashSet
     *
     * @param point coordinates to be add to snake body
     */
    final void add(final Point point)
    {
        freePoints.remove(point);
        snakeBody.addFirst(point);
    }

    /**
     * method calculating size of snake
     * @return length of snake
     */
    final int size()
    {
        return snakeBody.size();
    }

    /**
     * method check if point contains to snake body
     *
     * @param point point to be checked
     * @return true if snake body contains point
     */
    final boolean contains(final Point point)
    {
        return snakeBody.contains(point);
    }
}