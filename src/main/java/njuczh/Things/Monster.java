package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.CreaturesMeet;
import njuczh.Skills.Shoot;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Monster extends Creature implements Runnable, Shoot {
    private static ArrayList<Bullet> bullets = null;
    private static ExecutorService bulletExecutor = null;
    protected int cureCount=0;
    public Monster() {
        this.battlefield = battlefield;
        image = new Image("monster.png");
        property = CreatureAttribute.BAD;
        moveFinished = false;
        health = 270;
        maxHelth = 270;
        attackPower = 40;
        denfensePower =40;
    }

    public boolean isCured() {
        return cureCount>0;
    }
    public void setCure() {
        cureCount = 3;
    }
    public String toString() {
        return "小怪";
    }

    @Override
    public Image getImage() {
        return image;
    }

    public static void setBullets(ArrayList<Bullet> bullets) {
        Monster.bullets = bullets;
    }

    public static void setBulletExecutor(ExecutorService bulletExecutor) {
        Monster.bulletExecutor = bulletExecutor;
    }
    @Override
    protected Position nextMove() {
        Random random = new Random();
        int choice = random.nextInt()%8;
        int i = getPosition().getI();
        int j = getPosition().getJ();
        Position next = new Position(getPosition().getX(),getPosition().getY());
        synchronized (battlefield) {

            if(i>0&&!battlefield[i-1][j].isEmpty()) {
                if(battlefield[i-1][j].getCreature().getProperty()==CreatureAttribute.GOOD) {
                    next.setI(i-1);
                    return next;
                }
            }
            else if(i<9&&!battlefield[i+1][j].isEmpty()) {
                if(battlefield[i+1][j].getCreature().getProperty()==CreatureAttribute.GOOD) {
                    next.setI(i+1);
                    return next;
                }
            }
            else if(j>0&&!battlefield[i][j-1].isEmpty()) {
                if(battlefield[i][j-1].getCreature().getProperty()==CreatureAttribute.GOOD) {
                    next.setJ(j-1);
                    return next;
                }
            }
            else if(j<17&&!battlefield[i][j+1].isEmpty()) {
                if(battlefield[i][j+1].getCreature().getProperty()==CreatureAttribute.GOOD) {
                    next.setJ(j+1);
                    return next;
                }
            }
        }

        if(trace.size() > 10) {
            return moveToCentralField();
        }

        if(choice ==0 && j <17) {
            next.setJ(j+1);
        }
        if(choice == 1 && i > 0) {
            next.setI(i-1);
        }
        if(choice > 2&&j>0) {
            next.setJ(j-1);
        }
        if(choice ==2&&i<9) {
            next.setI(i+1);
        }
        return next;
    }
    public void run() {
        Random random = new Random();
        //现阶段采取避让策略
        boolean timeToShoot = false;
        while(health !=0) {
            Position next = nextMove();
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
        Bullet bullet = new Bullet(toString(), BulletAttribute.EVIL,bulletPos,battlefield);
        return bullet;
    }
}
