package View;

import model.Model;

import javax.swing.*;
import java.awt.*;

public class Arena extends JPanel
{
    private final Model model;
    private final View view;

    Color text;

    Image snake;
    Image snakeHead;
    Image apple;

    Arena(Model model, View view)
    {
        this.model = model;
        this.view = view;
        setPreferredSize(new Dimension(model.PROPORTION * model.WIDTH, model.PROPORTION * model.HEIGHT));
        setBackground(new Color(255, 0, 102));
    }

    public void paint(Graphics graphics)
    {
        super.paint(graphics);
        switch (model.getGameState())
        {

            case PLAYING:
            {
                drawSnake(graphics);
                Toolkit.getDefaultToolkit().sync();
                break;
            }
            case GAME_OVER:
                drawGameOver(graphics);
                break;
            case READY:
                drawPressEnter(graphics);
                drawSnake(graphics);
                break;
        }

    }

    private void drawSnake(Graphics graphics)
    {
        Point point = model.getSnake(0);
        graphics.drawImage(snakeHead, point.x * model.PROPORTION, point.y * model.PROPORTION, model.PROPORTION, model.PROPORTION, this);
        for (int i = 1; i < model.getSnakeSize(); ++i)
        {
            point = model.getSnake(i);
            graphics.drawImage(snake, point.x * model.PROPORTION, point.y * model.PROPORTION, model.PROPORTION, model.PROPORTION, this);
        }
        for (int i = 0; i < model.getApplesSize(); ++i)
        {
            point = model.getApple(i);
            graphics.drawImage(apple, point.x * model.PROPORTION, point.y * model.PROPORTION, model.PROPORTION, model.PROPORTION, this);
        }
    }

    private void drawPressEnter(Graphics graphics)
    {
        String string = "Press ENTER to start game";
        graphics.setColor(text);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        int x = (model.WIDTH * model.PROPORTION - graphics.getFontMetrics().stringWidth(string)) / 2;
        int y = (model.HEIGHT * model.PROPORTION) / 2;
        graphics.drawString(string, x, y);
    }

    private void drawGameOver(Graphics graphics)
    {
        String gameOver = "GAME OVER";
        String score = "score:";
        graphics.setColor(text);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        int x = (model.WIDTH * model.PROPORTION - graphics.getFontMetrics().stringWidth(gameOver)) / 2;
        int y = (model.HEIGHT * model.PROPORTION) / 5;
        graphics.drawString(gameOver, x, y);
    }
}
