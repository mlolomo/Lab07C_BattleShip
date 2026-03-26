import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel missLabel;
    private JLabel strikeLabel;
    private JLabel totalHitLabel;
    private JLabel totalMissLabel;

    public ScorePanel() {
        setLayout(new GridLayout(2, 4, 10, 4));
        setBorder(BorderFactory.createTitledBorder("Scoreboard"));

        add(makeHeader("Miss [0-5]"));
        add(makeHeader("Strikes [0-3]"));
        add(makeHeader("Total Hits"));
        add(makeHeader("Total Misses"));

        missLabel      = makeValue("0");
        strikeLabel    = makeValue("0");
        totalHitLabel  = makeValue("0");
        totalMissLabel = makeValue("0");

        add(missLabel);
        add(strikeLabel);
        add(totalHitLabel);
        add(totalMissLabel);
    }

    private JLabel makeHeader(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }

    private JLabel makeValue(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 22));
        lbl.setForeground(new Color(0, 100, 160));
        return lbl;
    }

    public void update(int miss, int strike, int totalHit, int totalMiss) {
        missLabel.setText(String.valueOf(miss));
        strikeLabel.setText(String.valueOf(strike));
        totalHitLabel.setText(String.valueOf(totalHit));
        totalMissLabel.setText(String.valueOf(totalMiss));

        strikeLabel.setForeground(strike >= 2 ? Color.RED : new Color(0, 100, 160));
    }

    public void reset() {
        update(0, 0, 0, 0);
        strikeLabel.setForeground(new Color(0, 100, 160));
    }
}