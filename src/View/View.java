package View;

import controller.KeysControl;
import model.Model;

import javax.swing.*;
import java.awt.*;

public class View
{
    public final Score score;
    public final Arena arena;
    private final JFrame jFrame;
    private final JPanel jPanel;
    private final KeysControl keysControl;
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public View(Model model, KeysControl keysControl)
    {
        this.keysControl = keysControl;
        arena = new Arena(model);
        score = new Score(model);

        jFrame = new JFrame("Snake");
        jPanel = new JPanel();
        intiWindow();
    }

    private void intiWindow()
    {
        jFrame.add(jPanel);
        jFrame.addKeyListener(keysControl);
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

    private void addPanel(int gridy, Component component)
    {
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        jPanel.add(component, gridBagConstraints);
    }

}
