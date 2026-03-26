import javax.swing.SwingUtilities;
public class BattleShipApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}