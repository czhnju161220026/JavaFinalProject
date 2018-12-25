package njuczh.Things;

import javafx.scene.image.*;
import njuczh.Attributes.CreatureAttribute;

public class DeadCreature extends Creature{
    private int count = 3000; //3秒钟后消失
    private Image body = new Image("dead.png");
    public DeadCreature() {
        property = CreatureAttribute.DEAD;
    }
    public Image getImage() {
        return body;
    }
    public boolean isTimeout() {
        return count == 0;
    }
    public void decreaseTime() {
        count-=50;
    }

    @Override
    public String getInfo() {
        return "D "+getPosition().toString();
    }
}
