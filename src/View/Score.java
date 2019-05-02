package View;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Score extends JPanel
{
    final JLabel jLabel;
    private final Model model;
    private final int SCORE_HEIGHT = 3;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    Score(Model model)
    {
        this.model = model;
        setPreferredSize(new Dimension(model.PROPORTION * model.WIDTH, model.PROPORTION * SCORE_HEIGHT));
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

    void updateScore()
    {
        jLabel.setText("Score: " + model.getScore() + " Best Score:" + model.getScore() + " Actual date: " + formatter.format(new Date(System.currentTimeMillis())));
    }
}
