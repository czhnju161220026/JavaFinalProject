package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Attributes.BulletCategory;
import njuczh.Attributes.BulletDirection;
import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.Skills.Shoot;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Monster extends Creature implements Runnable, Shoot {
    protected static ArrayList<Bullet> bullets = null;
    protected static ExecutorService bulletExecutor = null;
    protected int cureCount=0;
    public Monster() {
        this.battlefield = battlefield;
        image = new Image("monster.png");
        property = CreatureAttribute.BAD;
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

        if(numOfSteps > 10) {
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
            numOfSteps ++;
            if(health<=0) {
                break;
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
        Bullet bullet = new Bullet(toString(), BulletCategory.EVIL, BulletDirection.LEFT,bulletPos,battlefield);
        return bullet;
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder("");
        info.append("M "+getPosition().toString());
        info.append(" "+getHelthRatio());
        if(isCured()) {
            info.append(" "+1);
        }
        else {
            info.append(" "+0);
        }
        return info.toString();
    }
}
