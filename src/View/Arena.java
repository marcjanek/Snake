package View;

import model.Model;

import javax.swing.*;
import java.awt.*;

public class Arena extends JPanel
{
    private final Model model;

    Arena(Model model)
    {
        this.model = model;
        setPreferredSize(new Dimension(model.PROPRTION * model.WIDTH, model.PROPRTION * model.HEIGHT));
        setBackground(new Color(6, 255, 0));
    }

    public void paint(Graphics graphics2D)
    {

        super.paint(graphics2D);
        switch (model.getGameState())
        {

            case PLAYING:
                drawSnake(graphics2D);
                break;
            case SCORES:
                drawScores(graphics2D);
                break;
            case WAITING:
                drawWaiting(graphics2D);
                break;
        }
    }

    private void drawSnake(Graphics graphics2D)
    {
        for (int i = 0; i < model.getSnakeSize(); ++i)
        {
            drawSnakeHelper(model.getSnake(i), graphics2D, model.getSnakeBorderColor(), model.getSnakeBackgroundColor());
        }
        for (int i = 0; i < model.getApplesSize(); ++i)
        {
            drawSnakeHelper(model.getApples(i), graphics2D, model.getAppleBorderColor(), model.getSnakeBackgroundColor());
        }
    }

    private void drawSnakeHelper(Point point, Graphics graphics2D, Color border, Color fill)
    {
        graphics2D.setColor(border);
        graphics2D.drawRect(point.x * model.PROPRTION, point.y * model.PROPRTION, model.PROPRTION, model.PROPRTION);
        graphics2D.setColor(fill);
        graphics2D.fillRect(point.x * model.PROPRTION, point.y * model.PROPRTION, model.PROPRTION, model.PROPRTION);
    }

    private void drawScores(Graphics graphics2D)
    {
        //TODO
    }

    private void drawWaiting(Graphics graphics2D)
    {
        String string = "Press arrow to start game";
        graphics2D.setColor(new Color(255, 255, 255));
        int x = (model.WIDTH - graphics2D.getFontMetrics().stringWidth(string)) / 2;
        int y = (model.HEIGHT - graphics2D.getFontMetrics().stringWidth(string)) / 2;
        graphics2D.drawString("Press arrow to start game", x, y);
    }
}
