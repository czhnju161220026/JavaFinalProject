package njuczh.Things;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.control.TextArea;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.Block;
import njuczh.Battle.BulletHit;
import njuczh.GUI.GameRound;
import njuczh.MyAnnotation.TODO;
import sun.misc.Queue;

import java.util.concurrent.TimeUnit;


public class Bullet extends Thing implements Runnable{
    private Image image;
    private int attackPower;
    private String shooterName;
    private boolean good;
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
        attackPower = 20;
        image = new Image(attribute.getImagePath());
        if(attribute==BulletAttribute.EVIL) {
            good = false;
            direction = -1;
        }
        else {
            good = true;
            direction = 1;
        }
    }
    @TODO(todo = "在图像上绘制自己")
    public void display(GraphicsContext gc) {
        if(attribute == BulletAttribute.FIRE || attribute == BulletAttribute.WATER) {
            gc.drawImage(image,pos.getX(),pos.getY()+15,20,40);
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

    public boolean isGood() {
        return good;
    }

    public BulletAttribute getAttribute() {
        return attribute;
    }

    public static void setHitQueue(Queue<BulletHit> Queue) {
        hitQueue= Queue;
    }
    @TODO(todo = "沿着指示的方向移动，检测撞击事件")
    public void run() {
        int i = pos.getY()/72;
        int j = pos.getX()/72;
        while(!isDone && pos.getX() > 0 && pos.getX() < 1296) {
            pos.setX(pos.getX()+ direction *18);
            if(pos.getX()%72 == 0) {
                j = pos.getX()/72;
                if(j==18) {
                    break;
                }
            }
            synchronized (battlefield) {
                if(!battlefield[i][j].isEmpty()) {
                    Creature creature = battlefield[i][j].getCreature();
                    if(good!=creature.getProperty()) {
                        BulletHit bulletHit = new BulletHit(this,creature);
                        synchronized (hitQueue) {
                            hitQueue.enqueue(bulletHit);
                        }
                        if(creature.isDead()) {
                            battlefield[i][j].creatureLeave();
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
