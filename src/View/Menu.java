package View;

import enums.Colors;
import enums.Level;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeMap;

class Menu extends JMenuBar
{
    private final Model model;
    private final View view;
    private gameListener gameListener;
    private JMenu newGame, snake, apple, music, readyViews, settings, backGround, text, arena, scoreBar;

    private JMenuItem easy, medium, hard, expert;

    Menu(Model model, View view)
    {
        this.model = model;
        this.view = view;
        gameListener = new gameListener();
        newGame();
        settings();
    }

    private void newGame()
    {
        newGame = new JMenu("new game");
        easy = new JMenuItem("easy");
        easy.addActionListener(gameListener);
        medium = new JMenuItem("medium");
        medium.addActionListener(gameListener);
        hard = new JMenuItem("hard");
        hard.addActionListener(gameListener);
        expert = new JMenuItem("expert");
        expert.addActionListener(gameListener);
        newGame.add(easy);
        newGame.add(medium);
        newGame.add(hard);
        newGame.add(expert);
        add(newGame);
    }

    private void settings()
    {
        settings = new JMenu("settings");
        readyViews();
        arena();
        scoreBar();
        snake();
        SimpleMenu("apple", new AppleListener(), view.music.keySet());
        SimpleMenu("music", new MusicListener(), view.music.keySet());
        add(settings);
    }

    private void readyViews()
    {
        readyViews = new JMenu("ready views");
        settings.add(readyViews);
    }

    private void arena()
    {
        arena = new JMenu("arena");
        new ColorChoseMenu(new ArenaText(), "text color", new ArenaBackground(), "background color", arena, view.colorsMap.keySet());
        settings.add(arena);
    }

    private void scoreBar()
    {
        scoreBar = new JMenu("score bar");
        new ColorChoseMenu(new ScoreBarText(), "text color", new ScoreBarBackground(), "background color", scoreBar, view.colorsMap.keySet());
        settings.add((scoreBar));
    }

    private void snake()
    {
        snake = new JMenu("snake");
        new ColorChoseMenu(new HeadSnakeListener(), "snake head", new BodySnakeListener(), "snake body", snake, view.snakeParts.keySet());
        settings.add(snake);
    }

    private void SimpleMenu(String name, ActionListener actionListener, Set keySet)
    {
        JMenu jMenu = new JMenu(name);
        new JMenuCreator(actionListener, jMenu, keySet);
        settings.add(jMenu);
    }

    private final class ColorChoseMenu extends JMenu
    {
        private final JMenu parent;
        private final Set<String> keySet;

        ColorChoseMenu(ActionListener actionListener, String name, ActionListener actionListener1, String name1, JMenu parent, Set keySet)
        {
            this.parent = parent;
            this.keySet = keySet;
            jMenuCreator(name, actionListener);
            jMenuCreator(name1, actionListener1);
        }

        private void jMenuCreator(String name, ActionListener actionListener)
        {
            JMenu jMenu = new JMenu(name);
            new JMenuCreator(actionListener, jMenu, keySet);
            parent.add(jMenu);
        }
    }

    private final class JMenuCreator extends JMenuItem
    {
        ActionListener actionListener;
        JMenu parent;

        JMenuCreator(ActionListener actionListener, JMenu parent, Set keys1)
        {
            this.actionListener = actionListener;
            this.parent = parent;
            Set<String> keys = keys1;
            for (String key : keys)
            {
                JMenuItem jMenuItem = new JMenuItem(key);
                jMenuItem.addActionListener(actionListener);
                parent.add(jMenuItem);
            }
        }
    }

    private class gameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (actionEvent.getSource() == easy)
            {
                model.restart(Level.EASY);
            } else if (actionEvent.getSource() == medium)
            {
                model.restart(Level.MEDIUM);
            } else if (actionEvent.getSource() == hard)
            {
                model.restart(Level.HARD);
            } else if (actionEvent.getSource() == expert)//expert
            {
                model.restart(Level.EXPERT);
            }
        }
    }

    private class ScoreBarBackground implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setScoreBarBackground(actionEvent.getActionCommand());
        }
    }

    private class ScoreBarText implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setScoreBarText(actionEvent.getActionCommand());
        }
    }

    private class ArenaBackground implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setArenaBackground(actionEvent.getActionCommand());
        }
    }

    private class ArenaText implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setArenaText(actionEvent.getActionCommand());
        }
    }

    private class MusicListener implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setMusic(actionEvent.getActionCommand());
        }
    }

    private class HeadSnakeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setSnakeHead(actionEvent.getActionCommand());
        }
    }

    private class BodySnakeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setSnakeBody(actionEvent.getActionCommand());
        }
    }

    private class AppleListener implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            view.setApple(actionEvent.getActionCommand());
        }
    }
}
