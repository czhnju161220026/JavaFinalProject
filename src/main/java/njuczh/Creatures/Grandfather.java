package njuczh.Creatures;
import njuczh.Skills.*;
import javafx.scene.image.Image;
public class Grandfather extends Creature implements Cheer,Runnable{
    public Grandfather() {
        image = new Image("grandfather.jpg");
        attackPower = 0;
        denfensePower = 0;
        helth = 0;
    }

    @Override
    public Image getImage() {
        return image;
    }
    @Override
    public String toString() {
        return "爷爷";
    }
    public void cheer(){}
    public void run() {

    }
}
