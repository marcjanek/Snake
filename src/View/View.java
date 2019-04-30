package View;

import controller.Controller;
import enums.Direction;

import model.Model;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class View implements KeyListener
{
    public final Score score;
    public final Arena arena;
    public final JFrame jFrame;
    private final JPanel jPanel;
    public Direction direction = Direction.RIGHT;
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private Controller controller;
    private Model model;

    public View(Model model)
    {
        this.model = model;
        arena = new Arena(model);
        score = new Score(model);

        jFrame = new JFrame("Snake");
        jPanel = new JPanel();
        intiWindow();
    }

    public void addController(Controller controller)
    {
        this.controller = controller;
    }

    private void addPanel(int gridy, Component component)
    {
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        jPanel.add(component, gridBagConstraints);
    }

    private void intiWindow()
    {
        jFrame.addKeyListener(this);
        jFrame.add(jPanel);
        jPanel.setLayout(new GridBagLayout());
        addPanel(1, score);
        addPanel(0, arena);

        //jFrame.setIconImagine();
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.toFront();
    }

    @Override
    public void keyPressed(KeyEvent evt)
    {
        int keyCode = evt.getKeyCode();
        switch (keyCode)
        {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            {
                direction = Direction.UP;
                break;
            }
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
            {
                direction = Direction.DOWN;
                break;
            }
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            {
                direction = Direction.LEFT;
                break;
            }
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
            {
                direction = Direction.RIGHT;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {}

    @Override
    public void keyTyped(KeyEvent evt) {}
}
