package model;

import enums.Direction;
import enums.Level;
import enums.States;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.Queue;

/**
 * class manages the data,logic and rules od application
 */
public final class Model {
    /**
     * arena width
     */
    public final int WIDTH = 53;
    /**
     * arena height
     */
    public final int HEIGHT = 26;
    /**
     * hashSet which contains all free coordinates in arena
     */

    @NotNull
    private final HashSet<Point> freePoints;
    /**
     * class representing snake body
     */

    @NotNull
    private final Snake snake;
    /**
     * class performing operations on fruits collection
     */

    @NotNull
    private final Fruits fruits;
    /**
     * //class accepts input and converts it to commands for the model or view
     */

    @NotNull
    private final Database dataBase;
    /**
     * best score in ended games
     */
    public volatile int bestScore = 0;
    /**
     * time of start game
     */
    public long startTimeOfGame;
    /**
     * time between move in milliseconds
     */
    public int speed = 500;
    /**
     * direction in last move
     */

    @NotNull
    public Direction lastDirection = Direction.RIGHT;
    /**
     * actual state of game
     */
    public volatile States actualState;
    /**
     * boolean representing true if game ended with collision, else false
     */
    public boolean collision = false;
    /**
     * current level of game
     */

    @NotNull
    private Level level = Level.MEDIUM;

    /**
     * constructor initialize classes: Snake, Fruits, Database
     */
    public Model() {
        dataBase = new Database();
        freePoints = new HashSet<>();
        initFreePoints();
        snake = new Snake(WIDTH, HEIGHT, freePoints);
        fruits = new Fruits(freePoints);
        actualState = States.READY;
    }

    /**
     * initializing collection with free coordinates
     */
    private void initFreePoints() {
        for (int i = 0; i < WIDTH; ++i)
            for (int j = 0; j < HEIGHT; ++j)
                freePoints.add(new Point(i, j));
    }

    /**
     * returning best records from database
     *
     * @param number number of best records
     * @return records in output format
     */

    @NotNull
    public final Queue<String> bestScores(final int number) {
        return dataBase.bestScores(number);
    }

    /**
     * returns new head coordinates
     *
     * @param direction direction in which snake's head move
     * @return new head coordinates
     */

    @NotNull
    private Point moveHead(@NotNull final Direction direction) {
        int x = snake.head().x;
        int y = snake.head().y;
        switch (direction) {
            case DOWN:
                return new Point(x, ++y);
            case LEFT:
                return new Point(--x, y);
            case RIGHT:
                return new Point(++x, y);
            case UP:
                return new Point(x, --y);
            default:
                return new Point(x, y);
        }
    }

    /**
     * method performing move of all snake and checking collecting fruits and collisions
     *
     * @param direction direction in which snake's head move
     */
    public final void moveSnake(@NotNull final Direction direction) {
        final Point newHead = moveHead(direction);
        if (fruits.contains(newHead)) {
            fruits.remove(newHead);
            snake.add(newHead);
            fruits.add(newHead);
        } else if (collision(newHead)) {
            collision = true;
            gameOver();
        } else {
            snake.removeTail();
            snake.add(newHead);
        }
    }

    /**
     * returns collection of fruits coordinates
     *
     * @return fruits collection coordinates
     */

    @NotNull
    public final Queue<Point> getFruits() {
        return fruits.get();
    }

    /**
     * change game state to game over and change best score if is higher then previous one and adds game statistic to database
     */
    private synchronized void gameOver() {
        actualState = States.GAME_OVER;
        bestScore = Math.max(getScore(), bestScore);
        final long currentTimeMillis = System.currentTimeMillis();
        dataBase.add(getScore(),
                currentTimeMillis,
                (currentTimeMillis - startTimeOfGame) / 1000,
                level.toString());
    }

    /**
     * checks if snakes head hit snakes body or hit wall
     *
     * @param newHead new head coordinates
     * @return true if hit, else false
     */
    private boolean collision(final Point newHead) {
        final Point head = snake.head();
        return snake.contains(newHead) || (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT);
    }

    /**
     * returns actual score
     *
     * @return actual score
     */
    public final int getScore() {
        return snake.size() - 1;
    }

    /**
     * returns snake's body coordinates
     *
     * @return snake body list
     */

    @NotNull
    public final Queue<Point> getSnake() {
        return snake.get();
    }

    /**
     * method to change level of game
     *
     * @param level new level of game
     */
    private void setLevelSettings(@NotNull final Level level) {
        this.level = level;
        switch (level) {
            case NOOB:
                speed = 500;
                fruits.maxFruits = 50;
                break;
            case MEDIUM:
                speed = 200;
                fruits.maxFruits = 20;
                break;
            case HARD:
                speed = 100;
                fruits.maxFruits = 10;
                break;
            case EXPERT:
                speed = 50;
                fruits.maxFruits = 1;
                break;
        }
    }

    /**
     * method restarts game on current level
     */
    public final void restart() {
        restart(level);
    }

    /**
     * method restarts game with settings in the parameters
     *
     * @param level new level of game
     */
    public final void restart(@NotNull final Level level) {
        setLevelSettings(level);
        initFreePoints();
        snake.reset();
        fruits.reset();
        lastDirection = Direction.RIGHT;
        actualState = States.READY;
    }
}

