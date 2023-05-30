import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int SPACESHIP_SPEED = 5;
    private static final int BULLET_SPEED = 8;
    private static final int INVADER_ROWS = 5;
    private static final int INVADER_COLUMNS = 12;
    private static final int INVADER_GAP = 10;

    private Spaceship spaceship;
    private List<Bullet> bullets;
    private List<Invader> invaders;
    private boolean isSpacePressed;
    private boolean isGameOver;
    private JButton resetButton;
    private Image spaceshipImage;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        spaceship = new Spaceship();
        bullets = new ArrayList<>();
        initializeInvaders();
        isGameOver = false;

        resetButton = new JButton("Reset");
        resetButton.setBounds(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2 + 50, 100, 30);
        resetButton.addActionListener(this);
        resetButton.setVisible(false);
        add(resetButton);

        try {
            BufferedImage originalImage = ImageIO.read(new File("images/spaceship.png"));
            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
            tx.translate(0, -originalImage.getHeight(null));
            spaceshipImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) spaceshipImage.getGraphics();
            g2d.drawImage(originalImage, tx, null);
            g2d.dispose();
        } catch (IOException e) {
            System.out.println("Failed to load spaceship image: " + e.getMessage());
        }

    }

    private void initializeInvaders() {
        invaders = new ArrayList<>();
        int startX = (PANEL_WIDTH - INVADER_COLUMNS * (Invader.DEFAULT_WIDTH + INVADER_GAP)) / 2;
        int startY = 50;


        try {
            BufferedImage invaderImage = ImageIO.read(new File("images/invader.png"));
            for (int row = 0; row < INVADER_ROWS; row++) {
                for (int col = 0; col < INVADER_COLUMNS; col++) {
                    int x = startX + col * (Invader.DEFAULT_WIDTH + INVADER_GAP);
                    int y = startY + row * (Invader.DEFAULT_HEIGHT + INVADER_GAP);
                    invaders.add(new Invader(x, y, invaderImage));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load invader image: " + e.getMessage());
        }
    }

    public void updateGame() {
        if (isGameOver) {
            return;
        }

        // Update spaceship position
        if (spaceship.isMovingLeft()) {
            spaceship.setX(spaceship.getX() - SPACESHIP_SPEED);
        } else if (spaceship.isMovingRight()) {
            spaceship.setX(spaceship.getX() + SPACESHIP_SPEED);
        }

        // Update bullet positions
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            bullet.setY(bullet.getY() - BULLET_SPEED);

            // Check if the bullet is out of bounds
            if (bullet.getY() + bullet.getHeight() < 0) {
                iterator.remove();
            } else {
                // Check for collision between bullet and invaders
                for (Iterator<Invader> invaderIterator = invaders.iterator(); invaderIterator.hasNext();) {
                    Invader invader = invaderIterator.next();
                    if (bullet.getBounds().intersects(invader.getBounds())) {
                        // Handle collision logic
                        iterator.remove();
                        invaderIterator.remove();
                        break;
                    }
                }
            }
        }

        // Check if all invaders have been eliminated
        if (invaders.isEmpty()) {
            isGameOver = true;
            resetButton.setVisible(true);
        }

        // Check for collision between spaceship and invaders
        for (Invader invader : invaders) {
            if (spaceship.getBounds().intersects(invader.getBounds())) {
                // Handle collision logic
                isGameOver = true;
                resetButton.setVisible(true);
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);

        if (isGameOver) {
            drawGameOver(g);
        }
    }

    private void drawGame(Graphics g) {
        // Clear the panel with a background color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Calculate the adjusted spaceship position
        int spaceshipX = spaceship.getX();
        int spaceshipY = spaceship.getY() - spaceship.getHeight();

        // Draw the spaceship image
        g.drawImage(spaceshipImage, spaceshipX, spaceshipY, spaceship.getWidth(), spaceship.getHeight(), null);

        // Draw bullets
        g.setColor(Color.RED);
        for (Bullet bullet : bullets) {
            g.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }

        // Draw invaders
        g.setColor(Color.BLUE);
        for (Invader invader : invaders) {
            g.drawImage(invader.getImage(), invader.getX(), invader.getY(), invader.getWidth(), invader.getHeight(), null);
        }
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String gameOverText = "GAME OVER";
        int textWidth = g.getFontMetrics().stringWidth(gameOverText);
        int x = (PANEL_WIDTH - textWidth) / 2;
        int y = PANEL_HEIGHT / 2;
        g.drawString(gameOverText, x, y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            spaceship.setMovingLeft(true);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            spaceship.setMovingRight(true);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            if (!isGameOver && !isSpacePressed) {
                isSpacePressed = true;
                Bullet bullet = new Bullet();
                bullet.setX(spaceship.getX() + spaceship.getWidth() / 2 - bullet.getWidth() / 2);
                bullet.setY(spaceship.getY() - bullet.getHeight());
                bullets.add(bullet);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            spaceship.setMovingLeft(false);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            spaceship.setMovingRight(false);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            isSpacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but must be implemented due to KeyListener interface
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetGame();
        }
    }

    private void resetGame() {
        spaceship = new Spaceship();
        bullets.clear();
        initializeInvaders();
        isGameOver = false;
        resetButton.setVisible(false);
        requestFocusInWindow();
    }
}
