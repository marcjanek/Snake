package View;

import controller.Controller;
import enums.Direction;
import enums.States;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class View
{
    final Map<String, Color> colorsMap = new TreeMap<>();
    final Map<String, String> appleMap = new TreeMap<>();
    final Map<String, String> snakeParts = new TreeMap<>();
    final Map<String, String> readyView = new TreeMap<>();
    public final Map<String, String> music = new TreeMap<>();
    private final JFrame jFrame;
    private final Score score;
    private final Arena arena;
    private final JPanel jPanel;
    private final Model model;
    private final Menu menu;
    //  private final Image apple;
    public Direction direction = Direction.RIGHT;
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private Controller controller;
    public View(Model model)
    {
        initColorsMap();
        initMusicMap();
        initAppleMap();
        initSnakeParts();
        initReadyViews();
        this.model = model;
        score = new Score(model);
        arena = new Arena(model, this);
        menu = new Menu(model, this);

        jFrame = new JFrame("Snake");
        jPanel = new JPanel();
        intiWindow();
    }

    private void initReadyViews()
    {
        readyView.put("dark", "");
        readyView.put("light", "");
        readyView.put("apple fan", "");
        readyView.put("girlish", "");
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
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

    private void initMusicMap()
    {
        music.put("mute", "");
        music.put("unmute", "");
        music.put("Pirates of the Caribbean", ".wav");
        music.put("Axel Foley - Original disco 80", ".wav");
    }

    private void initAppleMap()
    {
        appleMap.put("apple logo", "apple0.png");
        appleMap.put("red apple", "apple1.png");
        appleMap.put("pineapple", "pineapple.png");
    }

    private void initSnakeParts()
    {
        snakeParts.put("black dot", "snake0.png");
        snakeParts.put("red dot", "snake1.png");
        snakeParts.put("blue dot", "snake2.png");
        snakeParts.put("yellow dot", "snake3.png");
        snakeParts.put("yellow star", "snake4.png");
        snakeParts.put("white dot", "snake5.png");
        snakeParts.put("black star", "snake6.png");
        snakeParts.put("white star", "snake7.png");
    }

    void setReadyViews(String name)
    {
        switch (name)
        {
            default:
            case "dark":
            {
                setReadyViewsHelper("black", "red", "crimson", "white", "yellow star", "white star", "red apple");
                break;
            }
            case "light":
            {
                setReadyViewsHelper("white", "blue", "gold", "coral", "red dot", "yellow dot", "red apple");
                break;
            }
            case "apple fan":
            {
                setReadyViewsHelper("gold", "red", "aqua", "gold", "black star", "white star", "apple logo");
                break;
            }
            case "girlish":
            {
                setReadyViewsHelper("deep pink", "olive", "coral", "gold", "white star", "white dot", "pineapple");
                break;
            }
        }
    }

    private void setReadyViewsHelper(String arenaBackground, String arenaText, String scoreBackground, String scoreText, String head, String body, String apple)
    {
        setArenaBackground(arenaBackground);
        setArenaText(arenaText);
        setScoreBarBackground(scoreBackground);
        setScoreBarText(scoreText);
        setSnakeHead(head);
        setSnakeBody(body);
        setApple(apple);
    }
    void setScoreBarBackground(String colorName)
    {
        score.setBackground(colorsMap.get(colorName));
    }

    void setScoreBarText(String colorName)
    {
        score.jLabel.setForeground(colorsMap.get(colorName));
    }

    void setArenaBackground(String colorName)
    {
        arena.setBackground(colorsMap.get(colorName));
    }

    void setArenaText(String colorName)
    {
        arena.text = colorsMap.get(colorName);
    }

    void setSnakeBody(String name)
    {
        arena.snake = new ImageIcon("src/pictures/" + snakeParts.get(name)).getImage();
    }

    void setSnakeHead(String name)
    {
        arena.snakeHead = new ImageIcon("src/pictures/" + snakeParts.get(name)).getImage();
    }

    void setMusic(String name)
    {
        if (name.equals("mute"))
            controller.muteMusic();
        else if (name.equals("unmute"))
            controller.unMuteMusic();
        else
            controller.changeSong(name);
    }

    void setApple(String name)
    {
        arena.apple = new ImageIcon("src/pictures/" + appleMap.get(name)).getImage();
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
        Random random = new Random();
        setReadyViews("dark");

        jFrame.setIconImage(new ImageIcon("src/pictures/snakeIcon.png").getImage());
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
