package view;

import model.Model;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class performing operations on score bar JPanel
 */
final class Score extends JPanel {
    /**
     * score bar JPanel
     */

    @NotNull
    final JLabel jLabel;
    /**
     * reference to class Model
     */

    @NotNull
    private final Model model;
    /**
     * output format to data
     */
    @NotNull
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    /**
     * creates score bar's JPanel
     *
     * @param model      reference to class Model
     * @param PROPORTION size of image
     */
    Score(@NotNull final Model model, final int PROPORTION) {
        final int SCORE_HEIGHT = 3;
        this.model = model;
        setPreferredSize(new Dimension(PROPORTION * model.WIDTH, PROPORTION * SCORE_HEIGHT));
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jLabel = new JLabel();
        add(jLabel, gridBagConstraints);
        updateScore();
    }

    /**
     * updates score bar
     */
    final void updateScore() {
        Toolkit.getDefaultToolkit().sync();
        jLabel.setText(
                "score: " + model.getScore() +
                        " best score:" + Math.max(model.bestScore, model.getScore()) +
                        " actual date: " + formatter.format(new Date(System.currentTimeMillis())));
    }
}
