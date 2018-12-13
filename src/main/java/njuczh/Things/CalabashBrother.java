package njuczh.Things;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.Block;
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
    private final Block[][] battlefield;
    private  static ExecutorService bulletExecutor=null;
    private  static ArrayList<Bullet> bullets=null;

    public CalabashBrother(Color color,Block[][] battlefield) {
        this.color = color;
        this.battlefield = battlefield;
        int index = color.ordinal()+1;
        image = new Image(""+index+".png");
        attackPower = 0;
        denfensePower = 0;
        health = 100;
        maxHelth = 100;
        good = true;
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
        int choice = random.nextInt()%8;
        int i = getPosition().getY()/72;
        int j = getPosition().getX()/72;
        Position next = new Position(getPosition().getX(),getPosition().getY());
        if(choice ==0 && j > 0) {
            next.setX(next.getX()-72);
        }
        if(choice == 1 && i > 0) {
            next.setY(next.getY()-72);
        }
        if(choice > 2&&j<17) {
            next.setX(next.getX()+72);
        }
        if(choice ==2&&i<9) {
            next.setY(next.getY()+72);
        }
        return next;
    }

    @TODO(todo = "随机行走,目前只采取避让策略，之后考虑碰撞事件")
    public void run() {
        boolean timeToShoot = true;
        //现阶段采取避让策略
        while(health>0) {
            Position next = nextMove();
            int i = next.getY()/72;
            int j = next.getX()/72;
            synchronized (battlefield) {
                if(battlefield[i][j].isEmpty()) {
                    battlefield[getPosition().getY()/72][getPosition().getX()/72].creatureLeave();
                    battlefield[i][j].creatureEnter(this);
                    setPosition(next.getX(),next.getY());
                }
                else {
                    Creature creature = battlefield[i][j].getCreature();
                    if(creature.getProperty()!=this.getProperty()) {
                        meetQueue.enqueue(new CreaturesMeet(this,creature));
                        if(isDead()) {
                            battlefield[getPosition().getY()/72][getPosition().getX()/72].creatureLeave();
                        }
                        else {
                            battlefield[i][j].creatureLeave();
                        }
                    }
                }
            }
            try{
                if(timeToShoot) {
                    shoot();
                }
                TimeUnit.MILLISECONDS.sleep(1000);
                timeToShoot = !timeToShoot;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shoot() {
        Position bulletPos = new Position(getPosition().getX()+72,getPosition().getY());
        BulletAttribute bulletAttribute = BulletAttribute.HERO;
        if(color == Color.GREEN) {
            bulletAttribute = BulletAttribute.FIRE;
        }
        if(color == Color.CYAN) {
            bulletAttribute = BulletAttribute.WATER;
        }
        Bullet bullet = new Bullet(this.toString(),bulletAttribute,bulletPos,battlefield);
        bulletExecutor.execute(bullet);
        synchronized (bullets) {
            bullets.add(bullet);
        }
    }
}
