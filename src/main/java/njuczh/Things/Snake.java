package njuczh.Things;

import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.CreaturesMeet;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.Cure;
import javafx.scene.image.Image;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Snake extends Monster implements Runnable, Cure {
    public Snake() {
        this.battlefield = battlefield;
        image = new Image("snake.png");
        property = CreatureAttribute.BAD;
        moveFinished = false;
        health = 500;
        maxHelth = 500;
        attackPower = 60;
        denfensePower =40;
    }
    public String toString() {
        return "蛇精";
    }
    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public boolean isCured() {
        return false;
    }
    //治疗周围的Monster
    public void cheer() {
        int i = getPosition().getI();
        int j = getPosition().getJ();
        synchronized (battlefield) {
            for(int m = i-2;m<=i+2;m++) {
                for(int n = j-2;n<=j+2;n++) {
                    if(m>0&&m<10&&n>0&&n<18) {
                        Creature creature = battlefield[m][n].getCreature();
                        if(creature instanceof Monster ) {
                            synchronized (creature) {
                                ((Monster)creature).setCure();
                            }
                        }
                    }
                }
            }
        }
    }
    @TODO(todo = "随机行走,目前只采取避让策略，走出界即结束。之后考虑碰撞事件")
    public void run() {
        if(!isReviewing) {
            trace.add(new Position(getPosition().getX(),getPosition().getY()));
        }
        Random random = new Random();
        //现阶段采取避让策略
        while(health !=0) {
            cheer();
            Position next = nextMove();
            int i = next.getI();
            int j = next.getJ();
            synchronized (battlefield) {
                if(health<=0) {
                    break;
                }
                if(!isReviewing) {
                    trace.add(next);
                }
                if(battlefield[i][j].isEmpty()) {
                    move(next);
                }
                else {
                    //trace.add(new Position(getPosition().getX(),getPosition().getY()));
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
                    else {
                        //如果因为队友占据了位置而阻塞了前进，重新记录移动轨迹
                        if(!isReviewing) {
                            trace.remove(trace.size()-1);
                            trace.add(new Position(getPosition().getX(),getPosition().getY()));
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
