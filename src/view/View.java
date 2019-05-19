package view;

import controller.Controller;
import enums.Direction;
import enums.States;
import model.Model;
import org.jetbrains.annotations.NotNull;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * user interface
 */
public class View {
    /**
     * collection with names of loaded clips
     */
    @NotNull
    public final Map<String, String> musicMap = new TreeMap<>();
    /**
     * collection with names of implemented colors
     */
    @NotNull
    final Map<String, Color> colorsMap = new TreeMap<>();
    /**
     * collection with names of fruits pictures
     */
    @NotNull
    final Map<String, String> fruitMap = new TreeMap<>();
    /**
     * collection with names of snakeBody body pictures
     */
    @NotNull
    final Map<String, String> snakeMap = new TreeMap<>();
    /**
     * collection with names of ready views
     */
    @NotNull
    final Map<String, String> readyView = new TreeMap<>();
    /**
     * reference to class Score
     */

    @NotNull
    private final Score score;
    /**
     * reference to class Arena
     */

    @NotNull
    private final Arena arena;
    /**
     * reference to class Model
     */

    @NotNull
    private final Model model;
    /**
     * reference to class controller
     */

    @NotNull
    private final Controller controller;
    /**
     * last pressed direction
     */

    @NotNull
    public Direction direction = Direction.RIGHT;

    /**
     * class constructor creates user interface
     *
     * @param model reference to class Model
     */
    public View(@NotNull final Model model, @NotNull final Controller controller) {
        this.model = model;
        this.controller = controller;
        final int PROPORTION = 23;
        initColorsMap();
        initMusicMap();
        initFruitMap();
        initSnakeParts();
        initReadyViews();
        score = new Score(model, PROPORTION);
        arena = new Arena(model, PROPORTION);
        final Menu menu = new Menu(this, controller);
        final JFrame jFrame = new JFrame("Snake");
        final JPanel jPanel = new JPanel();
        jFrame.add(menu);
        jFrame.setJMenuBar(menu);
        jFrame.addKeyListener(new Keys());
        jFrame.add(jPanel);
        jPanel.setLayout(new GridBagLayout());
        final class AddPanel {
            private AddPanel(final int gridy, @NotNull final Component component) {
                final GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridy = gridy;
                jPanel.add(component, gridBagConstraints);
            }
        }
        new AddPanel(0, score);
        new AddPanel(1, arena);
        setReadyViews("dark");
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("pictures/snakeIcon.png");
            jFrame.setIconImage(new ImageIcon(ImageIO.read(Objects.requireNonNull(inputStream))).getImage());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.toFront();
    }

    /**
     * sets color of score bar background
     *
     * @param colorName name of new color
     */
    final void setScoreBarBackground(final String colorName) {
        score.setBackground(colorsMap.get(colorName));
    }

    /**
     * sets color of score bar text
     *
     * @param colorName name of new color
     */
    final void setScoreBarText(final String colorName) {
        score.jLabel.setForeground(colorsMap.get(colorName));
    }

    /**
     * sets color of arena background
     *
     * @param colorName name of new color
     */
    final void setArenaBackground(final String colorName) {
        arena.setBackground(colorsMap.get(colorName));
    }

    /**
     * sets color of arena text
     *
     * @param colorName name of new color
     */
    final void setArenaText(final String colorName) {
        arena.text = colorsMap.get(colorName);
        arena.repaint();
    }

    /**
     * sets picture of snakeBody's body
     *
     * @param name name of snakeBody's picture
     */
    final void setSnakeBody(final String name) {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("pictures/" + snakeMap.get(name));
            arena.snakeBody = new ImageIcon(ImageIO.read(Objects.requireNonNull(inputStream))).getImage();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * sets picture of snakeBody's head
     *
     * @param name of snakeBody's picture
     */
    final void setSnakeHead(final String name) {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("pictures/" + snakeMap.get(name));
            arena.snakeHead = new ImageIcon(ImageIO.read(Objects.requireNonNull(inputStream))).getImage();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * change, mute or unmute music
     *
     * @param name name of song or "mute" to mute music or "unmute" to unmute music
     */
    final void setMusic(@NotNull final String name) {
        if (name.equals("mute"))
            controller.muteMusic();
        else if (name.equals("unmute"))
            controller.unMuteMusic();
        else
            controller.changeSong(name);
    }

    /**
     * sets new fruit icon
     *
     * @param name name of file
     */
    final void setFruit(final String name) {

        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("pictures/" + fruitMap.get(name));
            arena.fruit = new ImageIcon(ImageIO.read(Objects.requireNonNull(inputStream))).getImage();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * repaints arena view
     */
    final public void paint() {
        arena.repaint();
    }

    /**
     * repaints score bar text
     */
    public final void paintScoreBar() {
        score.updateScore();
    }

    /**
     * puts names of ready views to collection
     */
    private void initReadyViews() {
        readyView.put("dark", "");
        readyView.put("light", "");
        readyView.put("girlish", "");
    }

    /**
     * puts names of colors to collection
     */
    private void initColorsMap() {
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

    /**
     * puts names of songs to collection
     */
    private void initMusicMap() {
        musicMap.put("mute", "");
        musicMap.put("unmute", "");
        musicMap.put("Pirates of the Caribbean", ".wav");
        musicMap.put("Axel Foley - Original disco 80", ".wav");
    }

    /**
     * puts names of fruits pictures to collection
     */
    private void initFruitMap() {
        fruitMap.put("red fruit", "apple1.png");
        fruitMap.put("pineapple", "pineapple.png");
    }

    /**
     * puts names of snakeBody body pictures to collection
     */
    private void initSnakeParts() {
        snakeMap.put("black dot", "snake0.png");
        snakeMap.put("red dot", "snake1.png");
        snakeMap.put("blue dot", "snake2.png");
        snakeMap.put("yellow dot", "snake3.png");
        snakeMap.put("yellow star", "snake4.png");
        snakeMap.put("white dot", "snake5.png");
        snakeMap.put("black star", "snake6.png");
        snakeMap.put("white star", "snake7.png");
    }

    /**
     * changes all view settings to ready view
     *
     * @param name name of ready view
     */
    final void setReadyViews(@NotNull final String name) {
        final class SetReadyViewsHelper {
            /**
             * helps with setting view
             *
             * @param arenaBackground color of arena background
             * @param arenaText       color of arena text
             * @param scoreBackground color of score bar background
             * @param scoreText       color of score bar text
             * @param head            name of picture to become snakeBody's head
             * @param body            name of picture to become snakeBody's body (without snakeBody's head)
             * @param fruit           name od picture to become fruit
             */
            private SetReadyViewsHelper(final String arenaBackground, final String arenaText, final String scoreBackground, final String scoreText, final String head, final String body, final String fruit) {
                setArenaBackground(arenaBackground);
                setArenaText(arenaText);
                setScoreBarBackground(scoreBackground);
                setScoreBarText(scoreText);
                setSnakeHead(head);
                setSnakeBody(body);
                setFruit(fruit);
            }
        }
        switch (name) {
            default:
            case "dark": {
                new SetReadyViewsHelper(
                        "black",
                        "red",
                        "crimson",
                        "white",
                        "yellow star",
                        "white star",
                        "red fruit");
                break;
            }
            case "light": {
                new SetReadyViewsHelper(
                        "white",
                        "blue",
                        "gold",
                        "coral",
                        "red dot",
                        "yellow dot",
                        "red fruit");
                break;

            }
            case "girlish": {
                new SetReadyViewsHelper(
                        "deep pink",
                        "olive",
                        "coral",
                        "gold",
                        "white star",
                        "white dot",
                        "pineapple");
                break;
            }
        }
    }

    /**
     * class performing keyboard input
     */
    private class Keys extends KeyAdapter {
        /**
         * changes last pressed key to input key
         *
         * @param evt pressed key
         */
        @Override
        public void keyPressed(@NotNull final KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W: {
                    direction = Direction.UP;
                    break;
                }
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S: {
                    direction = Direction.DOWN;
                    break;
                }
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A: {
                    direction = Direction.LEFT;
                    break;
                }
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D: {
                    direction = Direction.RIGHT;
                    break;
                }
                case KeyEvent.VK_ENTER: {
                    if (model.actualState == States.READY) {
                        direction = Direction.RIGHT;
                        model.actualState = States.PLAYING;
                    } else if (model.actualState == States.GAME_OVER) {
                        controller.setRestart();
                    }
                }
            }
        }
    }
}
