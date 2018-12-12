package njuczh.Things;
import njuczh.Attributes.*;
import javafx.scene.image.Image;
import njuczh.Battle.Block;

public abstract class Creature extends Thing{
    private Position position= new Position();
    protected Image image;
    protected  int attackPower;
    protected  int denfensePower;
    protected  int helth;
    protected  int maxHelth;
    protected boolean good;
    protected boolean moveFinished;
    public int getAttackPower() {
        return attackPower;
    }
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }
    public int getDenfensePower() {
        return denfensePower;
    }
    public void setDenfensePower(int denfensePower) {
        this.denfensePower = denfensePower;
    }
    public float getHelthRatio() {
        return (float)helth / (float)maxHelth;
    }
    public int getHelth() {
        return helth;
    }
    public void setHelth(int helth) {
        this.helth = helth;
    }
    public void setPosition(int x,int y) {
        position.setX(x);
        position.setY(y);
    }
    public Position getPosition() {
        return  position;
    }
    public abstract Image getImage();
    public boolean getProperty() {
        return good;
    }
}
