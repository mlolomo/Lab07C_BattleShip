import javax.swing.*;
import java.awt.*;

public class BattleCellButton extends JButton {
    private int row;
    private int col;

    // show characters
    private static final String BLANK_TEXT = "~";
    private static final String HIT_TEXT   = "X";
    private static final String MISS_TEXT  = "M";

    // Colors
    private static final Color BLANK_COLOR = new Color(173, 216, 230); // light blue
    private static final Color HIT_COLOR   = new Color(220,  50,  50); // red
    private static final Color MISS_COLOR  = new Color(255, 215,   0); // yellow

    public BattleCellButton(int row, int col) {
        this.row = row;
        this.col = col;
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setPreferredSize(new Dimension(48, 48));
        reset();
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public void showHit() {
        setText(HIT_TEXT);
        setBackground(HIT_COLOR);
        setForeground(Color.WHITE);
        setEnabled(false);
    }

    public void showMiss() {
        setText(MISS_TEXT);
        setBackground(MISS_COLOR);
        setForeground(Color.DARK_GRAY);
        setEnabled(false);
    }

    public void reset() {
        setText(BLANK_TEXT);
        setBackground(BLANK_COLOR);
        setForeground(new Color(0, 80, 140));
        setEnabled(true);
    }
}