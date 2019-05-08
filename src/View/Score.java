package View;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jLabel = new JLabel();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jLabel, gridBagConstraints);
        updateScore();
    }

    void updateScore()
    {
        Toolkit.getDefaultToolkit().sync();
        jLabel.setText("Score: " + model.getScore() + " Best Score:" + Math.max(model.bestScore, model.getScore()) + " Actual date: " + formatter.format(new Date(System.currentTimeMillis())));
    }
}
