package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.CreaturesMeet;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.Shoot;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scorpion extends Monster implements Runnable, Shoot {
    public Scorpion() {
        image = new Image("scorpion.png");
        property = CreatureAttribute.BAD;
        moveFinished = false;
        health = 600;
        maxHelth = 600;
        attackPower = 60;
        denfensePower = 60;
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
        if(!isReviewing) {
            trace.add(new Position(getPosition().getX(),getPosition().getY()));
        }
        boolean timeToShoot = false;
        //现阶段采取避让策略
        while(health !=0) {
            Position next = nextMove();
            if(health<=0) {
                break;
            }
            if(!isReviewing) {
                trace.add(next);
            }
            fight(next);

            try{
                if(timeToShoot) {
                    Bullet bullet =shoot();
                    bulletExecutor.execute(bullet);
                    synchronized (bullets) {
                        bullets.add(bullet);
                    }
                }
                timeToShoot = !timeToShoot;
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
    public Bullet shoot() {
        Position bulletPos = new Position(getPosition().getX()-72,getPosition().getY());
        Bullet bullet = new Bullet(this.toString(), BulletAttribute.STINGER,bulletPos,battlefield);
        return bullet;
    }
}
