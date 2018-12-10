package njuczh.Creatures;

import njuczh.Skills.Cheer;
import javafx.scene.image.Image;
public class Snake extends Creature implements Runnable, Cheer {
    public Snake() {
        image = new Image("snake.png");
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
