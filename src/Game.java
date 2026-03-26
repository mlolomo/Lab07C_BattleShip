import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Game extends JFrame {

    private static final int MAX_MISS    = 5;
    private static final int MAX_STRIKES = 3;

    private Board               board;
    private BattleCellButton[][] cells;
    private ScorePanel          scorePanel;
    private JLabel              statusLabel;

    private int missCounter;
    private int strikeCounter;
    private int totalHits;
    private int totalMisses;
    private boolean gameOver;

    public Game() {
        super("Lab07C – Battleship");
        board = new Board();
        cells = new BattleCellButton[Board.SIZE][Board.SIZE];
        buildUI();
        resetCounters();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(8, 8));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        statusLabel = new JLabel("Fire away! Click a cell.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(statusLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(Board.SIZE, Board.SIZE, 2, 2));
        gridPanel.setBackground(Color.DARK_GRAY);
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                BattleCellButton btn = new BattleCellButton(r, c);
                btn.addActionListener(this::handleCellClick);
                cells[r][c] = btn;
                gridPanel.add(btn);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        scorePanel = new ScorePanel();
        add(scorePanel, BorderLayout.SOUTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        JButton playAgainBtn = new JButton("Play Again");
        JButton quitBtn      = new JButton("Quit");

        playAgainBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        quitBtn.setFont(new Font("Arial", Font.PLAIN, 13));

        playAgainBtn.addActionListener(e -> onPlayAgain());
        quitBtn.addActionListener(e -> onQuit());

        btnPanel.add(playAgainBtn);
        btnPanel.add(quitBtn);
        add(btnPanel, BorderLayout.EAST);
    }

    private void handleCellClick(ActionEvent e) {
        if (gameOver) return;

        BattleCellButton btn = (BattleCellButton) e.getSource();
        int row = btn.getRow();
        int col = btn.getCol();

        boolean hit = board.fireAt(row, col);

        if (hit) {
            btn.showHit();
            totalHits++;
            missCounter = 0;

            Ship ship = board.getShipAt(row, col);
            if (ship.isSunk()) {
                JOptionPane.showMessageDialog(this,
                        "You sunk a ship of size " + ship.getSize() + "!",
                        "Ship Sunk!", JOptionPane.INFORMATION_MESSAGE);
            }

            if (board.allShipsSunk()) {
                gameOver = true;
                statusLabel.setText("🎉 You WIN! All ships sunk!");
                scorePanel.update(missCounter, strikeCounter, totalHits, totalMisses);
                int choice = JOptionPane.showConfirmDialog(this,
                        "You sunk all the ships! Play again?",
                        "You Win!", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) resetGame();
                else dispose();
                return;
            }
        } else {
            btn.showMiss();
            totalMisses++;
            missCounter++;

            if (missCounter >= MAX_MISS) {
                missCounter = 0;
                strikeCounter++;
                statusLabel.setText("STRIKE " + strikeCounter + "! 5 misses in a row!");

                if (strikeCounter >= MAX_STRIKES) {
                    gameOver = true;
                    statusLabel.setText("💀 3 Strikes — You LOSE!");
                    scorePanel.update(missCounter, strikeCounter, totalHits, totalMisses);
                    int choice = JOptionPane.showConfirmDialog(this,
                            "3 strikes — you're out! Play again?",
                            "You Lose!", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) resetGame();
                    else dispose();
                    return;
                }
            }
        }

        scorePanel.update(missCounter, strikeCounter, totalHits, totalMisses);
        statusLabel.setText(hit ? "HIT! Keep going!" : "Miss. " + missCounter + "/5 misses.");
    }

    private void resetCounters() {
        missCounter   = 0;
        strikeCounter = 0;
        totalHits     = 0;
        totalMisses   = 0;
        gameOver      = false;
    }

    public void resetGame() {
        board.reset();
        resetCounters();
        for (int r = 0; r < Board.SIZE; r++)
            for (int c = 0; c < Board.SIZE; c++)
                cells[r][c].reset();
        scorePanel.reset();
        statusLabel.setText("New game! Fire away!");
    }

    private void onPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Start a new game? Current progress will be lost.",
                "Play Again?", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) resetGame();
    }

    private void onQuit() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit?",
                "Quit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) dispose();
    }
}