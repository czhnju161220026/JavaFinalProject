package njuczh.Things;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.Position;
import njuczh.Battle.Block;
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
        helth = 10;
        maxHelth = 10;
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

    @TODO(todo = "随机行走,目前只采取避让策略，之后考虑碰撞事件")
    public void run() {
        Random random = new Random();
        boolean timeToShoot = true;
        //现阶段采取避让策略
        while(helth!=0) {
            int choice = random.nextInt()%6;
            int i = getPosition().getY()/70;
            int j = getPosition().getX()/70;
            synchronized (battlefield) {
                if(choice == 0 && j>0) {
                    if(battlefield[i][j-1].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i][j-1].creatureEnter(this);
                        setPosition((j-1)*70,i*70);
                    }
                }
                else if(choice == 1 && i>0) {
                    if(battlefield[i-1][j].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i-1][j].creatureEnter(this);
                        setPosition(j*70,(i-1)*70);
                    }
                }
                else if((choice ==2 || choice==4||choice==5) && j<17) {
                    if(battlefield[i][j+1].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i][j+1].creatureEnter(this);
                        setPosition((j+1)*70,i*70);
                    }
                }
                else if(choice == 3 && i<9) {
                    if( battlefield[i+1][j].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i+1][j].creatureEnter(this);
                        setPosition(j*70,(i+1)*70);
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
        Position bulletPos = new Position(getPosition().getX()+70,getPosition().getY());
        BulletAttribute bulletAttribute = BulletAttribute.HERO;
        if(color == Color.GREEN) {
            bulletAttribute = BulletAttribute.FIRE;
        }
        if(color == Color.CYAN) {
            bulletAttribute = BulletAttribute.WATER;
        }
        Bullet bullet = new Bullet(this.toString(),bulletAttribute,bulletPos);
        bulletExecutor.execute(bullet);
        synchronized (bullets) {
            bullets.add(bullet);
        }
    }
}
