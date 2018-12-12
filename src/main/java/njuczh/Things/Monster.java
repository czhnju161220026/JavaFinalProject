package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.Block;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.Shoot;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Monster extends Creature implements Runnable, Shoot {
    private final Block[][] battlefield;
    private static ArrayList<Bullet> bullets = null;
    private static ExecutorService bulletExecutor = null;

    public Monster(Block[][] battlefield) {
        this.battlefield = battlefield;
        image = new Image("monster.png");
        good = false;
        moveFinished = false;
        health = 30;
        maxHelth = 30;
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

    @TODO(todo = "随机行走,目前只采取避让策略，走出界即结束。之后考虑碰撞事件")
    public void run() {
        Random random = new Random();
        //现阶段采取避让策略
        boolean timeToShoot = false;
        while(health !=0) {
            int choice = random.nextInt()%4;
            int i = getPosition().getY()/72;
            int j = getPosition().getX()/72;
            synchronized (battlefield) {
                if(choice == 0 && j>0) {
                    if(battlefield[i][j-1].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i][j-1].creatureEnter(this);
                        setPosition((j-1)*72,i*72);
                    }
                }
                else if(choice == 1 && i>0) {
                    if(battlefield[i-1][j].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i-1][j].creatureEnter(this);
                        setPosition(j*72,(i-1)*72);
                    }
                }
                else if(choice == 2 && j<17) {
                    if(battlefield[i][j+1].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i][j+1].creatureEnter(this);
                        setPosition((j+1)*72,i*72);
                    }
                }
                else if(choice == 3 && i<9) {
                    if( battlefield[i+1][j].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i+1][j].creatureEnter(this);
                        setPosition(j*72,(i+1)*72);
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
        Position bulletPos = new Position(getPosition().getX()-72,getPosition().getY());
        Bullet bullet = new Bullet(toString(), BulletAttribute.EVIL,bulletPos,battlefield);
        bulletExecutor.execute(bullet);
        synchronized (bullets) {
            bullets.add(bullet);
        }
    }
}
