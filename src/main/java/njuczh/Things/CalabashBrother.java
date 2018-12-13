package njuczh.Things;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.CreaturesMeet;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.*;
import njuczh.Attributes.Color;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class CalabashBrother extends Creature implements Shoot,Runnable{
    private Color color;
    private int cureCount = 0;
    private  static ExecutorService bulletExecutor=null;
    private  static ArrayList<Bullet> bullets=null;

    public boolean isCured() {
        return cureCount > 0;
    }

    public void setCure() {
        cureCount = 3;
    }
    public CalabashBrother(Color color) {
        this.color = color;
        int index = color.ordinal()+1;
        image = new Image(""+index+".png");
        attackPower = 30;
        denfensePower = 30;
        health = 400;
        maxHelth = 400;
        property = CreatureAttribute.GOOD;
    }

    @Override
    public Image getImage() {
        return image;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color.getName();
    }

    public static void setBulletExecutor(ExecutorService executor) {
        bulletExecutor = executor;
    }

    public static void setBullets(ArrayList<Bullet> array) {
        bullets = array;
    }

    @Override
    Position nextMove() {
        Random random = new Random();
        int i = getPosition().getI();
        int j = getPosition().getJ();
        Position next = new Position(getPosition().getX(),getPosition().getY());
        synchronized (battlefield) {
            if(i>0&&!battlefield[i-1][j].isEmpty()) {
                if(battlefield[i-1][j].getCreature().getProperty()==CreatureAttribute.BAD) {
                    next.setI(i-1);
                    return next;
                }
            }
            else if(i<9&&!battlefield[i+1][j].isEmpty()) {
                if(battlefield[i+1][j].getCreature().getProperty()==CreatureAttribute.BAD) {
                    next.setI(i+1);
                    return next;
                }
            }
            else if(j>0&&!battlefield[i][j-1].isEmpty()) {
                if(battlefield[i][j-1].getCreature().getProperty()==CreatureAttribute.BAD) {
                    next.setJ(j-1);
                    return next;
                }
            }
            else if(j<17&&!battlefield[i][j+1].isEmpty()) {
                if(battlefield[i][j+1].getCreature().getProperty()==CreatureAttribute.BAD) {
                    next.setJ(j+1);
                    return next;
                }
            }
        }
        if(trace.size() > 15) {
            return moveToCentralField();
        }
        //周围没有敌人，随机行走
        int choice = random.nextInt()%8;
        if(choice ==0 && j > 0) {
            next.setJ(j-1);
        }
        if(choice == 1 && i > 0) {
            next.setI(i-1);
        }
        if(choice > 2&&j<17) {
            next.setJ(j+1);
        }
        if(choice ==2&&i<9) {
            next.setI(i+1);
        }
        return next;
    }

    @TODO(todo = "随机行走,目前只采取避让策略，之后考虑碰撞事件")
    public void run() {
        boolean timeToShoot = true;
        while(health>0) {
            Position next = nextMove();
            int i = next.getI();
            int j = next.getJ();
            synchronized (battlefield) {
                if(battlefield[i][j].isEmpty()) {
                    trace.add(next);
                    move(next);
                }
                else {
                    trace.add(new Position(getPosition().getX(),getPosition().getY()));
                    Creature creature = battlefield[i][j].getCreature();
                    if(creature.getProperty()==CreatureAttribute.BAD) {
                        meetQueue.enqueue(new CreaturesMeet(this,creature));
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
                    Bullet bullet = shoot();
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
        Position bulletPos = new Position(getPosition().getX()+72,getPosition().getY());
        BulletAttribute bulletAttribute = BulletAttribute.HERO;
        if(color == Color.GREEN) {
            bulletAttribute = BulletAttribute.FIRE;
        }
        if(color == Color.CYAN) {
            bulletAttribute = BulletAttribute.WATER;
        }
        Bullet bullet = new Bullet(this.toString(),bulletAttribute,bulletPos,battlefield);
        return bullet;
    }
}
