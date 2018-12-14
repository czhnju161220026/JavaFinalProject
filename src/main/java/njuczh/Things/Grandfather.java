package njuczh.Things;
import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.CreaturesMeet;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.*;
import javafx.scene.image.Image;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Grandfather extends Creature implements Cure,Runnable{
    public Grandfather() {
        image = new Image("grandfather.png");
        attackPower = 20;
        denfensePower = 20;
        health = 500;
        maxHelth = 500;
        property = CreatureAttribute.GOOD;
        moveFinished = false;
    }

    @Override
    public Image getImage() {
        return image;
    }
    @Override
    public String toString() {
        return "爷爷";
    }
    @TODO(todo = "治疗周围的葫芦娃")
    public void cheer(){
        int i = getPosition().getI();
        int j = getPosition().getJ();
        synchronized (battlefield) {
            for(int m = i-2;m<=i+2;m++) {
                for(int n = j-2;n<=j+2;n++) {
                    if(m>0&&m<10&&n>0&&n<18) {
                        Creature creature = battlefield[m][n].getCreature();
                        if(creature instanceof CalabashBrother) {
                            synchronized (creature) {
                                ((CalabashBrother) creature).setCure();
                            }
                        }
                    }
                }
            }
        }
    }
    @TODO(todo = "随机行走,目前只采取避让策略，走出界即结束。之后考虑碰撞事件")
    public void run() {
        while(health !=0) {
            cheer();
            Position next = moveToCentralField();
            int i = next.getI();
            int j = next.getJ();
            synchronized (battlefield) {
                if(health<=0) {
                    break;
                }
                if(battlefield[i][j].isEmpty()) {
                    trace.add(next);
                    move(next);
                }
                else {
                    trace.add(new Position(getPosition().getX(),getPosition().getY()));
                    Creature creature = battlefield[i][j].getCreature();
                    if(creature.getProperty()==CreatureAttribute.BAD) {
                        synchronized (meetQueue) {
                            meetQueue.enqueue(new CreaturesMeet(this,creature));
                        }
                        DeadCreature dead = new DeadCreature();
                        if(isDead()) {
                            battlefield[getPosition().getI()][getPosition().getJ()].creatureLeave();
                            dead.setPosition(getPosition().getX(),getPosition().getY());
                            battlefield[getPosition().getI()][getPosition().getJ()].creatureEnter(dead);
                        }
                        else {
                            battlefield[i][j].creatureLeave();
                            dead.setPosition(next.getX(),next.getY());
                            battlefield[i][j].creatureEnter(dead);
                        }
                    }
                }
            }
            try{
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
