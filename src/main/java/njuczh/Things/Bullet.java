package njuczh.Things;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.Block;
import njuczh.Battle.BulletHit;
import njuczh.MyAnnotation.TODO;
import java.util.Queue;

import java.util.concurrent.TimeUnit;


public class Bullet extends Thing implements Runnable{
    private Image image;
    private int attackPower;
    private String shooterName;
    private CreatureAttribute target;
    private Position pos;
    private int direction;
    private boolean isDone = false;
    private final Block[][] battlefield;
    private static Queue<BulletHit> hitQueue;

    private BulletAttribute attribute;
    public Bullet(String shooterName, BulletAttribute attribute, Position pos,Block[][] battlefield) {
        this.shooterName = shooterName;
        this.pos = pos;
        this.attribute = attribute;
        this.battlefield = battlefield;
        attackPower = 60;
        image = new Image(attribute.getImagePath());
        if(attribute==BulletAttribute.EVIL||attribute==BulletAttribute.STINGER) {
            target = CreatureAttribute.GOOD;
            direction = -1;
            if(attribute == BulletAttribute.STINGER) {
                attackPower = 90;
            }
        }
        else {
            target = CreatureAttribute.BAD;
            direction = 1;
            if(attribute == BulletAttribute.FIRE||attribute == BulletAttribute.WATER) {
                attackPower = 110;
            }
        }
    }
    @TODO(todo = "在图像上绘制自己")
    public void display(GraphicsContext gc) {
        if(attribute == BulletAttribute.FIRE || attribute == BulletAttribute.WATER||attribute==BulletAttribute.STINGER) {
            gc.drawImage(image,pos.getX(),pos.getY()+15,40,20);
        }
        else {
            gc.drawImage(image,pos.getX(),pos.getY()+31,10,10);
        }
    }
    public void setDone() {
        isDone = true;
    }
    public boolean isDone() {
        return isDone;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public String getShooterName() {
        return shooterName;
    }

    public CreatureAttribute getTarget() {
        return target;
    }

    public BulletAttribute getAttribute() {
        return attribute;
    }

    public static void setHitQueue(Queue<BulletHit> Queue) {
        hitQueue= Queue;
    }
    @TODO(todo = "沿着指示的方向移动，检测撞击事件")
    public void run() {
        int i = pos.getI();
        int j = pos.getJ();
        while(!isDone && pos.getX() > 0 && pos.getX() < 1296) {
            pos.setX(pos.getX()+ direction *18);
            if(pos.getX()%72 == 0) {
                j = pos.getJ();
                if(j==18) {
                    break;
                }
            }
            synchronized (battlefield) {
                if(!battlefield[i][j].isEmpty()) {
                    Creature creature = battlefield[i][j].getCreature();
                    if(target == creature.getProperty()) {
                        BulletHit bulletHit = new BulletHit(this,creature);
                        synchronized (hitQueue) {
                            hitQueue.offer(bulletHit);
                        }
                        if(creature.isDead()) {
                            battlefield[i][j].creatureLeave();
                            DeadCreature dead = new DeadCreature();
                            dead.setPosition(creature.getPosition().getX(),creature.getPosition().getY());
                            battlefield[i][j].creatureEnter(dead);
                        }
                    }
                }
            }
            try{
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isDone = true;
        //System.out.println("Bullet done");
    }

    public Position getPos() {
        return pos;
    }
}
