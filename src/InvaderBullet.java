import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class InvaderBullet {
    public static final int DEFAULT_WIDTH = 5;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_SPEED = 3;

    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;

    public InvaderBullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.speed = DEFAULT_SPEED;
    }

    public void move() {
        this.y += speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
// Add getter and setter methods...
}
