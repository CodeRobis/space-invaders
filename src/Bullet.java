import java.awt.Rectangle;

public class Bullet {
    private static final int WIDTH = 5;
    private static final int HEIGHT = 15;
    private static final int INITIAL_X = 0;
    private static final int INITIAL_Y = 0;

    private int x;
    private int y;
    private boolean isActive;

    public Bullet() {
        x = INITIAL_X;
        y = INITIAL_Y;
        isActive = false;
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

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void reset() {
        x = INITIAL_X;
        y = INITIAL_Y;
        isActive = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
