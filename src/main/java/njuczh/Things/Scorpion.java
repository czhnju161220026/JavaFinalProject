package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.CreaturesMeet;
import njuczh.MyAnnotation.TODO;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scorpion extends Monster implements Runnable {
    public Scorpion() {
        image = new Image("scorpion.png");
        property = CreatureAttribute.BAD;
        moveFinished = false;
        health = 600;
        maxHelth = 600;
        attackPower = 70;
        denfensePower = 50;
    }
    public String toString() {
        return "蝎子";
    }
    @Override
    public Image getImage() {
        return image;
    }

    @TODO(todo = "随机行走,目前只采取避让策略，走出界即结束。之后考虑碰撞事件")
    public void run() {
        Random random = new Random();
        //现阶段采取避让策略
        while(health !=0) {
            Position next = nextMove();
            int i = next.getI();
            int j = next.getJ();
            synchronized (battlefield) {
                if(health<=0) {
                    break;
                }
                if(health<=0) {
                    break;
                }
                if(battlefield[i][j].isEmpty()) {
                    move(next);
                    trace.add(next);
                }
                else {
                    trace.add(new Position(getPosition().getX(),getPosition().getY()));
                    Creature creature = battlefield[i][j].getCreature();
                    if(creature.getProperty()==CreatureAttribute.GOOD) {
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
                synchronized (this) {
                    if(cureCount>0&&health>0) {
                        health+=20;
                        if(health > maxHelth) {
                            health = maxHelth;
                        }
                    }
                    cureCount--;
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
