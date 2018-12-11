package njuczh.Things;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class Bullet extends Thing {
    private Image image;
    private int attackPower;
    public Bullet() {
        image = new Image("bullet.png");
    }

    public Image getImage() {
        return image;
    }
}
