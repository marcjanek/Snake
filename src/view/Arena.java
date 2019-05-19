package view;

import model.Model;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

import static java.util.Objects.requireNonNull;

/**
 * class representing arena
 */
@SuppressWarnings("JavaDoc")
final class Arena extends JPanel {
    /**
     * reference to class Model
     */

    @NotNull
    private final Model model;
    /**
     * size of image
     */
    private final int PROPORTION;
    /**
     * user interface picture width
     */
    private final int WIDTH;
    /**
     * user interface picture height
     */
    private final int HEIGHT;
    /**
     * color of arena text
     */
    Color text;
    /**
     * image of snake's body (without snake's head)
     */
    Image snakeBody;
    /**
     * image of snake's head
     */
    Image snakeHead;
    /**
     * image of fruit
     */
    Image fruit;

    /**
     * creates JPanel with size of arena
     *
     * @param model      reference to JMenu class
     * @param PROPORTION size of picture
     */
    Arena(@NotNull final Model model, final int PROPORTION) {
        this.model = model;
        this.PROPORTION = PROPORTION;
        WIDTH = PROPORTION * model.WIDTH;
        HEIGHT = PROPORTION * model.HEIGHT;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    /**
     * paints view depending of actual state
     *
     * @param graphics
     */
    public final void paint(@NotNull final Graphics graphics) {
        super.paint(graphics);
        Toolkit.getDefaultToolkit().sync();
        switch (model.actualState) {
            case PLAYING: {
                drawSnake(graphics);
                break;
            }
            case GAME_OVER:
                drawGameOver(graphics);
                drawBestScores(graphics);
                break;
            case READY:
                drawPressEnter(graphics);
                drawSnake(graphics);
                break;
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * draws list of best scores
     *
     * @param graphics
     */
    private void drawBestScores(@NotNull final Graphics graphics) {
        graphics.setColor(text);
        final String bestScores = "Best scores:";
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        int x = (WIDTH - graphics.getFontMetrics().stringWidth(bestScores)) / 2;
        int y = (HEIGHT) / 2;
        graphics.drawString(bestScores, x, y);
        final int bestScoreHeight = graphics.getFontMetrics().getHeight();

        final int MAX_SCORES = 9;
        final Queue<String> scores = model.bestScores(MAX_SCORES);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        int i = 0;
        for (String s : scores) {
            x = (WIDTH - graphics.getFontMetrics().stringWidth(s)) / 2;
            y = HEIGHT / 2 + i * graphics.getFontMetrics().getHeight() + bestScoreHeight;
            graphics.drawString(s, x, y);
            ++i;
        }
    }

    /**
     * draws snake and fruits on arena
     *
     * @param graphics
     */
    private void drawSnake(@NotNull final Graphics graphics) {
        final Queue<Point> snakeBody = model.getSnake();
        Point head = snakeBody.poll();
        graphics.drawImage(snakeHead, requireNonNull(head).x * PROPORTION, requireNonNull(head).y * PROPORTION, PROPORTION, PROPORTION, this);
        for (final Point body : snakeBody) {
            graphics.drawImage(this.snakeBody, body.x * PROPORTION, body.y * PROPORTION, PROPORTION, PROPORTION, this);
        }
        for (final Point fruit : model.getFruits()) {
            graphics.drawImage(this.fruit, fruit.x * PROPORTION, fruit.y * PROPORTION, PROPORTION, PROPORTION, this);
        }
    }

    /**
     * draws press enter text
     *
     * @param graphics
     */
    private void drawPressEnter(@NotNull final Graphics graphics) {
        final String string = "Press ENTER to start game";
        graphics.setColor(text);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        int x = (WIDTH - graphics.getFontMetrics().stringWidth(string)) / 2;
        int y = HEIGHT / 2;
        graphics.drawString(string, x, y);
    }

    /**
     * draws "game over" text and "Press ENTER to play again" text
     *
     * @param graphics
     */
    private void drawGameOver(@NotNull final Graphics graphics) {
        int x, y;
        final String gameOver = "GAME OVER";
        graphics.setColor(text);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        x = (WIDTH - graphics.getFontMetrics().stringWidth(gameOver)) / 2;
        y = HEIGHT / 5;
        graphics.drawString(gameOver, x, y);

        final String playAgain = "Press ENTER to play again";
        y = HEIGHT / 5 + graphics.getFontMetrics().getHeight();
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        x = (WIDTH - graphics.getFontMetrics().stringWidth(playAgain)) / 2;
        graphics.drawString(playAgain, x, y);
    }
}
