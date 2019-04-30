package View;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Timer;

public class Arena extends JPanel
{
    private final Model model;

    Arena(Model model)
    {
        this.model = model;
        setPreferredSize(new Dimension(model.PROPRTION * model.WIDTH, model.PROPRTION * model.HEIGHT));
        setBackground(new Color(6, 255, 0));
    }

    public void paint(Graphics graphics)
    {

        super.paint(graphics);
        switch (model.getGameState())
        {

            case PLAYING:
                drawSnake(graphics);
                break;
            case SCORES:
                drawScores(graphics);
                break;
            case WAITING:
                drawWaiting(graphics);
                break;
        }
    }

    private void drawSnake(Graphics graphics)
    {
        for (int i = 0; i < model.getSnakeSize(); ++i)
        {
            drawSnakeHelper(model.getSnake(i), graphics, model.getSnakeBorderColor(), model.getSnakeBackgroundColor());
        }
        for (int i = 0; i < model.getApplesSize(); ++i)
        {
            drawSnakeHelper(model.getApples(i), graphics, model.getAppleBorderColor(), model.getSnakeBackgroundColor());
        }
    }

    private void drawSnakeHelper(Point point, Graphics graphics, Color border, Color fill)
    {
        //graphics.setColor(border);
        graphics.drawRect(point.x * model.PROPRTION, point.y * model.PROPRTION, model.PROPRTION, model.PROPRTION);
        graphics.setColor(fill);
        graphics.fillRect(point.x * model.PROPRTION, point.y * model.PROPRTION, model.PROPRTION, model.PROPRTION);
    }

    private void drawScores(Graphics graphics)
    {
        //TODO
    }

    private void drawWaiting(Graphics graphics)
    {
        String string = "Press arrow to start game";
        graphics.setColor(new Color(255, 255, 255));
        int x = (model.WIDTH - graphics.getFontMetrics().stringWidth(string)) / 2;
        int y = (model.HEIGHT - graphics.getFontMetrics().stringWidth(string)) / 2;
        graphics.drawString("Press arrow to start game", x, y);
    }
}
