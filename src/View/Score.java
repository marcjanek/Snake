package View;

import model.Model;

import javax.swing.*;
import java.awt.*;

public class Score extends JPanel
{
    private final Model model;
    private final JLabel jLabel;
    private final int SCORE_HEIGHT = 3;

    Score(Model model)
    {
        this.model = model;
        setPreferredSize(new Dimension(model.PROPRTION * model.WIDTH, model.PROPRTION * SCORE_HEIGHT));
        setBackground(new Color(0, 0, 0));
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jLabel = new JLabel();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jLabel, gridBagConstraints);
        jLabel.setForeground(new Color(255, 255, 255));
        updateScore();

    }

    public void updateScore()
    {
        jLabel.setText("Score: " + model.getScore() + " Best Score:" + model.getScore() + " Actual date: ");
    }
}
