package njuczh.Creatures;

import javafx.scene.image.Image;
import njuczh.Skills.Shoot;

public class Monster extends Creature implements Runnable, Shoot {
    public Monster() {
        image = new Image("monster.png");
    }
    public String toString() {
        return "小怪";
    }
    @Override
    public Image getImage() {
        return image;
    }
    public void shoot() { }
    public void run() { }

}
