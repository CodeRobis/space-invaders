import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Invader {
    public static final int DEFAULT_WIDTH = 40;
    public static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_SPEED = 2;

    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage image;
    private int direction; // -1 for left, 1 for right
    private int speed;

    public Invader(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.image = image;
        this.direction = 1;
        this.speed = DEFAULT_SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void changeDirection() {
        this.direction *= -1;
        this.y += height; // move down a step when direction changes
    }

    public void move() {
        this.x += direction * speed;
    }

    public int getDirection() {
        return this.direction;
    }
}