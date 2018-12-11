package njuczh.Things;

import njuczh.Battle.Block;
import njuczh.Skills.Cure;
import javafx.scene.image.Image;
public class Snake extends Creature implements Runnable, Cure {
    private final Block[][] battlefield;
    public Snake(Block[][] battlefield) {
        this.battlefield = battlefield;
        image = new Image("snake.png");
        good = false;
    }
    public String toString() {
        return "蛇精";
    }
    @Override
    public Image getImage() {
        return image;
    }

    public void cheer() {}
    public void run() {}
}
