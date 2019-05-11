package view;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

final class Score extends JPanel
{
    final JLabel jLabel;
    private final Model model;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    Score(final Model model, final int PROPORTION)
    {
        final int SCORE_HEIGHT = 3;
        this.model = model;
        setPreferredSize(new Dimension(PROPORTION * model.WIDTH, PROPORTION * SCORE_HEIGHT));
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jLabel = new JLabel();
        add(jLabel, gridBagConstraints);
        updateScore();
    }

    final void updateScore()
    {
        Toolkit.getDefaultToolkit().sync();
        jLabel.setText(
                "score: " + model.getScore() +
                        " best score:" + Math.max(model.bestScore, model.getScore()) +
                        " actual date: " + formatter.format(new Date(System.currentTimeMillis())));
    }
}
