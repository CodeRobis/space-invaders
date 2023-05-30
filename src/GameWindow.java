import javax.swing.JFrame;

public class GameWindow extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private GamePanel gamePanel;

    public GameWindow() {
        setTitle("Space Invaders");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }

    public void startGameLoop() {
        while (true) {
            gamePanel.updateGame();
            gamePanel.repaint();

            try {
                Thread.sleep(16); // Adjust the sleep time to control game speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
