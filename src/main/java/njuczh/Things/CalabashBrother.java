package njuczh.Things;
import njuczh.Battle.Block;
import njuczh.Skills.*;
import njuczh.Attributes.Color;
import javafx.scene.image.Image;

import java.util.Comparator;

public class CalabashBrother extends Creature implements Comparator<CalabashBrother>,Shoot,Runnable{
    private Color color;
    private final Block[][] battlefield;
    public CalabashBrother(Color color,Block[][] battlefield) {
        this.color = color;
        this.battlefield = battlefield;
        int index = color.ordinal()+1;
        image = new Image(""+index+".png");
        attackPower = 0;
        denfensePower = 0;
        helth = 0;
        good = true;
    }
    @Override
    public Image getImage() {
        return image;
    }
    public Color getColor() {
        return color;
    }
    public int compare(CalabashBrother a,CalabashBrother b) {
        if(a.getColor().ordinal() < b.getColor().ordinal()) {
            return -1;
        }
        else if(a.getColor().ordinal() == b.getColor().ordinal()) {
            return 0;
        }
        return 1;
    }
    @Override
    public String toString() {
        return color.getName();
    }
    public void run() {

    }
    public Bullet shoot() { return null;}
}
