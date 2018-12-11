package njuczh.Things;

import njuczh.Skills.Cure;
import javafx.scene.image.Image;
public class Snake extends Creature implements Runnable, Cure {
    public Snake() {
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
