package view;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Queue;

final class Arena extends JPanel
{
    private final Model model;
    private final int PROPORTION;
    private final int WIDTH;
    private final int HEIGHT;
    Color text;

    Image snake;
    Image snakeHead;
    Image apple;

    Arena(final Model model, final int PROPORTION)
    {
        this.model = model;
        this.PROPORTION = PROPORTION;
        WIDTH = PROPORTION * model.WIDTH;
        HEIGHT = PROPORTION * model.HEIGHT;
        setPreferredSize(new Dimension(PROPORTION * model.WIDTH, PROPORTION * model.HEIGHT));
    }

    public final void paint(final Graphics graphics)
    {
        super.paint(graphics);
        Toolkit.getDefaultToolkit().sync();
        switch (model.actualState)
        {
            case PLAYING:
            {
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

    private void drawBestScores(final Graphics graphics)
    {
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
        for (String s : scores)
        {
            x = (WIDTH - graphics.getFontMetrics().stringWidth(s)) / 2;
            y = HEIGHT / 2 + i * graphics.getFontMetrics().getHeight() + bestScoreHeight;
            graphics.drawString(s, x, y);
            ++i;
        }
    }

    private void drawSnake(final Graphics graphics)
    {
        final Queue<Point> snakeBody = model.getSnake();
        Point head = snakeBody.poll();
        graphics.drawImage(snakeHead, Objects.requireNonNull(head).x * PROPORTION, head.y * PROPORTION, PROPORTION, PROPORTION, this);
        for (final Point body : snakeBody)
        {
            graphics.drawImage(snake, body.x * PROPORTION, body.y * PROPORTION, PROPORTION, PROPORTION, this);
        }
        for (final Point fruit : model.getFruits())
        {
            graphics.drawImage(apple, fruit.x * PROPORTION, fruit.y * PROPORTION, PROPORTION, PROPORTION, this);
        }
    }

    private void drawPressEnter(final Graphics graphics)
    {
        final String string = "Press ENTER to start game";
        graphics.setColor(text);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        int x = (WIDTH - graphics.getFontMetrics().stringWidth(string)) / 2;
        int y = HEIGHT / 2;
        graphics.drawString(string, x, y);
    }

    private void drawGameOver(final Graphics graphics)
    {
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
