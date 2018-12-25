package njuczh.Things;
import njuczh.Attributes.*;
import javafx.scene.image.Image;
import njuczh.Battle.Block;
import njuczh.Battle.CreaturesMeet;
import java.util.Queue;

import java.util.ArrayList;

public abstract class Creature extends Thing{
    private Position position= new Position();
    protected Image image;
    protected  int attackPower;
    protected  int denfensePower;
    protected  int health;
    protected  int maxHelth;
    protected CreatureAttribute property;
    protected int numOfSteps = 0;
    protected static Block[][] battlefield;
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
    public void die() {
        health = 0;
    }
    public void setPosition(int x,int y) {
        position.setX(x);
        position.setY(y);
    }
    public Position getPosition() {
        return  position;
    }
    public CreatureAttribute getProperty() {
        return property;
    }

    public static void setMeetQueue(Queue<CreaturesMeet> queue) {
        meetQueue = queue;
    }
    public static void setBattlefield(Block[][] field) {
        battlefield = field;
    }

    //抽象方法
    public abstract Image getImage();
    public abstract String getInfo();

    Position nextMove() {return null;}
    public void moveTo(Position pos) {
        battlefield[this.position.getI()][this.position.getJ()].creatureLeave();
        battlefield[pos.getI()][pos.getJ()].creatureEnter(this);
        this.position.setX(pos.getX());
        this.position.setY(pos.getY());
    }
    public boolean isCured() {
        return false;
    }

    public Position moveToCentralField() {
        int i = position.getI();
        int j = position.getJ();
        Position next = new Position(position.getX(),position.getY());
        if(i<5) {
            next.setI(i+1);
        }
        else if(i>5) {
            next.setI(i-1);
        }
        else if(j<10) {
            next.setJ(j+1);
        }
        else if(j>10) {
            next.setJ(j-1);
        }
        else {
            next.setI(i+1);
        }
        return next;
    }

    protected void fight(Position nextPos) {
        int i = nextPos.getI();
        int j = nextPos.getJ();
        synchronized (battlefield) {
            if(battlefield[i][j].isEmpty()) {
                moveTo(nextPos);

            }
            else {
                Creature creature = battlefield[i][j].getCreature();
                if(creature.getProperty()!=property&&creature.getProperty()!=CreatureAttribute.DEAD) {
                    synchronized (meetQueue) {
                        meetQueue.offer(new CreaturesMeet(this,creature));
                    }
                    DeadCreature dead = new DeadCreature();
                    if(isDead()) {
                        battlefield[getPosition().getI()][getPosition().getJ()].creatureLeave();
                        dead.setPosition(getPosition().getX(),getPosition().getY());
                        battlefield[getPosition().getI()][getPosition().getJ()].creatureEnter(dead);
                    }
                    else {
                        battlefield[i][j].creatureLeave();
                        dead.setPosition(nextPos.getX(), nextPos.getY());
                        battlefield[i][j].creatureEnter(dead);
                    }
                }
            }
        }
    }
}
