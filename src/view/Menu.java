package view;

import enums.Level;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

class Menu extends JMenuBar
{
    private final Model model;
    private final View view;
    private JMenu settings;

    Menu(Model model, View view)
    {
        this.model = model;
        this.view = view;
        newGame();
        settings();
    }

    private void newGame()
    {
        JMenu newGame = new JMenu("new game");
        final class GameListener implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                for (Level level : Level.values())
                    if (level.name().toLowerCase().equals(actionEvent.getActionCommand()))
                        model.restart(level);
            }
        }
        final GameListener gameListener = new GameListener();
        final class Helper
        {
            private Helper(final String name)
            {
                JMenuItem jMenuItem = new JMenuItem(name.toLowerCase());
                jMenuItem.addActionListener(gameListener);
                newGame.add(jMenuItem);
            }
        }
        for (final Level level : Level.values())
            new Helper(level.toString());
        add(newGame);
    }

    private void settings()
    {
        final class MusicListener implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setMusic(actionEvent.getActionCommand());
            }
        }
        final class AppleListener implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setApple(actionEvent.getActionCommand());
            }
        }
        final class ReadyViewsListener implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setReadyViews(actionEvent.getActionCommand());
            }
        }
        settings = new JMenu("settings");
        SimpleMenu("ready views", new ReadyViewsListener(), view.readyView.keySet());
        arena();
        scoreBar();
        snake();
        SimpleMenu("apple", new AppleListener(), view.appleMap.keySet());
        SimpleMenu("music", new MusicListener(), view.music.keySet());
        add(settings);
    }

    private void arena()
    {
        final class ArenaBackground implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setArenaBackground(actionEvent.getActionCommand());
            }
        }
        final class ArenaText implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setArenaText(actionEvent.getActionCommand());
            }
        }
        JMenu arena = new JMenu("arena");
        new DoubleMenu(new ArenaText(), "text color", new ArenaBackground(), "background color", arena, view.colorsMap.keySet());
        settings.add(arena);
    }

    private void scoreBar()
    {
        final class ScoreBarBackground implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setScoreBarBackground(actionEvent.getActionCommand());
            }
        }
        final class ScoreBarText implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setScoreBarText(actionEvent.getActionCommand());
            }
        }
        JMenu scoreBar = new JMenu("score bar");
        new DoubleMenu(new ScoreBarText(), "text color", new ScoreBarBackground(), "background color", scoreBar, view.colorsMap.keySet());
        settings.add((scoreBar));
    }

    private void snake()
    {
        final class BodySnakeListener implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setSnakeBody(actionEvent.getActionCommand());
            }
        }
        final class HeadSnakeListener implements ActionListener
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                view.setSnakeHead(actionEvent.getActionCommand());
            }
        }
        JMenu snake = new JMenu("snake");
        new DoubleMenu(new HeadSnakeListener(), "snake head", new BodySnakeListener(), "snake body", snake, view.snakeParts.keySet());
        settings.add(snake);
    }

    private void SimpleMenu(String name, ActionListener actionListener, Set<String> keySet)
    {
        JMenu jMenu = new JMenu(name);
        new JMenuCreator(actionListener, jMenu, keySet);
        settings.add(jMenu);
    }

    private final class DoubleMenu extends JMenu
    {
        private final JMenu parent;
        private final Set<String> keySet;

        DoubleMenu(ActionListener actionListener, String name, ActionListener actionListener1, String name1, JMenu parent, Set<String> keySet)
        {
            this.parent = parent;
            this.keySet = keySet;
            create(name, actionListener);
            create(name1, actionListener1);
        }

        private void create(String name, ActionListener actionListener)
        {
            JMenu jMenu = new JMenu(name);
            new JMenuCreator(actionListener, jMenu, keySet);
            parent.add(jMenu);
        }
    }

    private final class JMenuCreator extends JMenuItem
    {
        private JMenuCreator(final ActionListener actionListener, final JMenu parent, final Set<String> keys)
        {
            for (String key : keys)
            {
                JMenuItem jMenuItem = new JMenuItem(key);
                jMenuItem.addActionListener(actionListener);
                parent.add(jMenuItem);
            }
        }
    }
}
