package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Battle.Block;
import njuczh.Skills.Shoot;

public class Monster extends Creature implements Runnable, Shoot {
    private final Block[][] battlefield;
    public Monster(Block[][] battlefield) {
        this.battlefield = battlefield;
        image = new Image("monster.png");
        good = false;
    }
    public String toString() {
        return "小怪";
    }
    @Override
    public Image getImage() {
        return image;
    }
    public Bullet shoot() {return null;}
    public void run() { }

}
