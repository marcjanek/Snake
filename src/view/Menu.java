package view;

import controller.Controller;
import enums.Level;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * class representing menu bar
 */
final class Menu extends JMenuBar
{
    /**
     * reference to class View
     */
    private final View view;
    /**
     * JMenu settings
     */
    private JMenu settings;

    Menu(final View view, @NotNull final Controller controller)
    {
        this.view = view;

        newGame(controller);
        settings();
    }

    /**
     * creates JMenu with possibility to change level od game
     */
    private void newGame(@NotNull final Controller controller)
    {
        final JMenu newGame = new JMenu("new game");
        final class GameListener implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                for (final Level level : Level.values())
                    if (level.name().toLowerCase().equals(actionEvent.getActionCommand()))
                        controller.setRestart(level);
            }
        }
        final GameListener gameListener = new GameListener();
        final class Helper
        {
            private Helper(@NotNull final String name)
            {
                final JMenuItem jMenuItem = new JMenuItem(name.toLowerCase());
                jMenuItem.addActionListener(gameListener);
                newGame.add(jMenuItem);
            }
        }
        for (final Level level : Level.values())
            new Helper(level.toString());
        add(newGame);
    }

    /**
     * creates JMenu with settings to change user interface
     */
    private void settings()
    {
        final class MusicListener implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setMusic(actionEvent.getActionCommand());
            }
        }
        final class AppleListener implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setFruit(actionEvent.getActionCommand());
            }
        }
        final class ReadyViewsListener implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setReadyViews(actionEvent.getActionCommand());
            }
        }
        settings = new JMenu("settings");
        SimpleMenu("ready views", new ReadyViewsListener(), view.readyView.keySet());
        arena();
        scoreBar();
        snake();
        SimpleMenu("fruit", new AppleListener(), view.fruitMap.keySet());
        SimpleMenu("sound", new MusicListener(), view.musicMap.keySet());
        add(settings);
    }

    /**
     * creates JMenu with arena settings
     */
    private void arena()
    {
        final class ArenaBackground implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setArenaBackground(actionEvent.getActionCommand());
            }
        }
        final class ArenaText implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setArenaText(actionEvent.getActionCommand());
            }
        }
        final JMenu arena = new JMenu("arena");
        new DoubleMenu(new ArenaText(), "text color", new ArenaBackground(), "background color", arena, view.colorsMap.keySet());
        settings.add(arena);
    }

    /**
     * creates JMenu with score bar settings
     */
    private void scoreBar()
    {
        final class ScoreBarBackground implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setScoreBarBackground(actionEvent.getActionCommand());
            }
        }
        final class ScoreBarText implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setScoreBarText(actionEvent.getActionCommand());
            }
        }
        final JMenu scoreBar = new JMenu("score bar");
        new DoubleMenu(new ScoreBarText(), "text color", new ScoreBarBackground(), "background color", scoreBar, view.colorsMap.keySet());
        settings.add((scoreBar));
    }

    /**
     * creates JMenu with snake settings
     */
    private void snake()
    {
        final class BodySnakeListener implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setSnakeBody(actionEvent.getActionCommand());
            }
        }
        final class HeadSnakeListener implements ActionListener
        {
            public void actionPerformed(@NotNull final ActionEvent actionEvent)
            {
                view.setSnakeHead(actionEvent.getActionCommand());
            }
        }
        final JMenu snake = new JMenu("snakeBody");
        new DoubleMenu(new HeadSnakeListener(), "snakeBody head", new BodySnakeListener(), "snakeBody body", snake, view.snakeMap.keySet());
        settings.add(snake);
    }

    /**
     * creates JMenu with JMenuItems from keySet
     *
     * @param name           name of new JMenu
     * @param actionListener actionListener to new JMenu
     * @param keySet         set with names of JMenuItems
     */
    private void SimpleMenu(final String name, final ActionListener actionListener, @NotNull final Set<String> keySet)
    {
        final JMenu jMenu = new JMenu(name);
        new JMenuCreator(actionListener, jMenu, keySet);
        settings.add(jMenu);
    }

    /**
     * class creating two JMenus
     */
    private final class DoubleMenu extends JMenu
    {
        private final JMenu parent;
        private final Set<String> keySet;

        /**
         * creates two JMenus
         *
         * @param actionListener  ActionListener to first JMenu
         * @param name            name of new first JMenu
         * @param actionListener1 ActionListener to second JMenu
         * @param name1           name of new second JMenu
         * @param parent          JMenu parent
         * @param keySet          set with names of JMenuItems
         */
        DoubleMenu(final ActionListener actionListener, final String name, final ActionListener actionListener1, final String name1, final JMenu parent, final Set<String> keySet)
        {
            this.parent = parent;
            this.keySet = keySet;
            create(name, actionListener);
            create(name1, actionListener1);
        }

        /**
         * creates simple JMenu
         *
         * @param name           name of new JMenu
         * @param actionListener ActionListener to new JMenu
         */
        private void create(final String name, final ActionListener actionListener)
        {
            final JMenu jMenu = new JMenu(name);
            new JMenuCreator(actionListener, jMenu, keySet);
            parent.add(jMenu);
        }
    }

    /**
     * creates JMenuItems with set names
     */
    private final class JMenuCreator extends JMenuItem
    {
        /**
         * creates JMenuItems with set names
         *
         * @param actionListener ActionListener to to new JMenu
         * @param parent         parent to which connect new JMenuItems
         * @param keys           key set with names of new JMenuItems
         */
        private JMenuCreator(final ActionListener actionListener, @NotNull final JMenu parent, @NotNull final Set<String> keys)
        {
            for (final String key : keys)
            {
                final JMenuItem jMenuItem = new JMenuItem(key);
                jMenuItem.addActionListener(actionListener);
                parent.add(jMenuItem);
            }
        }
    }
}
