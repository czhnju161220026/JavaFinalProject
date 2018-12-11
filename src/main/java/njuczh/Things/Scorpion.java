package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Skills.Summon;

public class Scorpion extends Creature implements Runnable, Summon {
    public Scorpion() {
        image = new Image("scorpion.png");
        good = false;
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
