import java.awt.Rectangle;

public class Spaceship {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;
    private static final int INITIAL_X = 375;
    private static final int INITIAL_Y = 550;

    private int x;
    private int y;
    private boolean isMovingLeft;
    private boolean isMovingRight;

    public Spaceship() {
        x = INITIAL_X;
        y = INITIAL_Y;
        isMovingLeft = false;
        isMovingRight = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
