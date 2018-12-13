package njuczh.Things;
import njuczh.Attributes.*;
import javafx.scene.image.Image;
import njuczh.Battle.CreaturesMeet;
import sun.misc.Queue;

public abstract class Creature extends Thing{
    private Position position= new Position();
    protected Image image;
    protected  int attackPower;
    protected  int denfensePower;
    protected  int health;
    protected  int maxHelth;
    protected boolean good;
    protected boolean moveFinished;
    protected static Queue<CreaturesMeet> meetQueue;
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
        return (float) health / (float)maxHelth;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public boolean isDead() {
        return health <=0;
    }
    public void kill() {
        health = 0;
    }
    public void setPosition(int x,int y) {
        position.setX(x);
        position.setY(y);
    }
    public Position getPosition() {
        return  position;
    }
    public boolean getProperty() {
        return good;
    }

    public static void setMeetQueue(Queue<CreaturesMeet> queue) {
        meetQueue = queue;
    }

    //抽象方法
    public abstract Image getImage();
    Position nextMove() {return null;}
}
