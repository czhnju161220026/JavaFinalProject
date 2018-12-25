package njuczh.Game;

import javafx.scene.image.Image;

public class GameElement {
    private Image image;
    private int x, y;
    private int width;
    private int height;
    private boolean alive = false;
    private boolean isCured = false;
    private float healthRatio = 0;
    public GameElement(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameElement(Image image, int x, int y, int width, int height, float healthRatio, int isCured) {
        this(image,x,y,width,height);
        this.healthRatio = healthRatio;
        if(isCured == 1) {
            this.isCured = true;
        }
        this.alive = true;
    }

    public Image getImage() {
        return image;
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

    public boolean isAlive() {
        return alive;
    }

    public boolean isCured() {
        return isCured;
    }

    public float getHealthRatio() {
        return healthRatio;
    }
}