package njuczh.Things;
import njuczh.Skills.*;
import javafx.scene.image.Image;
public class Grandfather extends Creature implements Cure,Runnable{
    public Grandfather() {
        image = new Image("grandfather.png");
        attackPower = 0;
        denfensePower = 0;
        helth = 0;
        good = true;
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
