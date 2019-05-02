package View;

import enums.Direction;

import enums.Level;
import enums.States;
import enums.ViewColor;
import model.Model;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class View
{
    final Map<String, Color> colorsMap = new TreeMap<>();
    final Map<String, String> appleMap = new TreeMap<>();
    final Map<String, String> snakeParts = new TreeMap<>();
    final Map<String, String> music = new TreeMap<>();
    private final JFrame jFrame;
    private final Score score;
    private final Arena arena;
    private final JPanel jPanel;
    private final Model model;
    private final Menu menu;
    //  private final Image apple;
    public Direction direction = Direction.RIGHT;
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public View(Model model)
    {
        initColorsMap();
        this.model = model;
        score = new Score(model);
        arena = new Arena(model, this);
        menu = new Menu(model, this);


        setPictures(ViewColor.DARK);
        jFrame = new JFrame("Snake");
        jPanel = new JPanel();
        intiWindow();
    }

    private void initColorsMap()
    {
        colorsMap.put("black", new Color(0, 0, 0));
        colorsMap.put("white", new Color(255, 255, 255));
        colorsMap.put("red", new Color(255, 0, 0));
        colorsMap.put("green", new Color(0, 128, 0));
        colorsMap.put("blue", new Color(0, 0, 255));
        colorsMap.put("olive", new Color(128, 128, 128));
        colorsMap.put("crimson", new Color(220, 20, 60));
        colorsMap.put("coral", new Color(255, 127, 80));
        colorsMap.put("aqua", new Color(0, 255, 255));
        colorsMap.put("deep pink", new Color(255, 20, 147));
        colorsMap.put("steel blue", new Color(70, 130, 180));
        colorsMap.put("gold", new Color(255, 215, 0));
    }

    void setScoreBarBackground(String colorName)
    {
        score.setBackground(colorsMap.get(colorName));
        paint();
    }

    void setScoreBarText(String colorName)
    {
        score.jLabel.setForeground(colorsMap.get(colorName));
        paint();
    }

    void setArenaBackground(String colorName)
    {
        arena.setBackground(colorsMap.get(colorName));
        paint();
    }

    void setArenaText(String colorName)
    {
        arena.text = colorsMap.get(colorName);
        paint();
    }

    void setSnakeBody(String name)
    {

    }

    void setSnakeHead(String name)
    {

    }

    void setMusic(String name)
    {

    }

    void setApple(String name)
    {

    }

    void readyView(String name)
    {

    }

    void setPictures(ViewColor viewColor)
    {
        switch (viewColor)
        {
            case DARK:
                arena.snake = new ImageIcon("src/pictures/black_dot.png").getImage();
                arena.apple = new ImageIcon("src/pictures/apple0.png").getImage();
                break;
            case LIGHT:
                break;
            case GIRLISH:
                break;
        }
        arena.snakeHead = arena.snake;
    }

    void setViewColor(ViewColor viewColor)
    {
        switch (viewColor)
        {
            case GIRLISH:
                break;
            case LIGHT:
                break;
            case DARK:
                break;
        }
    }

    private void addPanel(int gridy, Component component)
    {
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        jPanel.add(component, gridBagConstraints);
    }

    public void paint()
    {
        arena.repaint();
    }

    public void paintScoreBar()
    {
        score.updateScore();
    }

    private void intiWindow()
    {
        jFrame.add(menu);
        jFrame.setJMenuBar(menu);
        jFrame.setSize(1000, 1000);
        jFrame.addKeyListener(new Keys());
        jFrame.add(jPanel);
        jPanel.setLayout(new GridBagLayout());
        addPanel(0, score);
        addPanel(1, arena);

        //jFrame.setIconImagine();
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.toFront();
    }

    private class Keys extends KeyAdapter
    {
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
                case KeyEvent.VK_ENTER:
                {
                    if (model.getGameState() == States.READY)
                    {
                        direction = Direction.RIGHT;
                        model.setGameState(States.PLAYING);
                    }
                }
            }
        }
    }
}
