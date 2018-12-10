package njuczh.Creatures;

import javafx.scene.image.Image;
import njuczh.Skills.Shoot;
import njuczh.Skills.Summon;

public class Scorpion extends Creature implements Runnable, Summon {
    public Scorpion() {
        image = new Image("scorpion.png");
    }
    public String toString() {
        return "蝎子";
    }
    @Override
    public Image getImage() {
        return image;
    }

    public void summon() {}
    public void run() {}

}
