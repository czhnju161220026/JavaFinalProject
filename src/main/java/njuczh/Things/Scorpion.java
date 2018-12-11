package njuczh.Things;

import javafx.scene.image.Image;
import njuczh.Attributes.Position;
import njuczh.Battle.Battlefield;
import njuczh.Battle.Block;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.Summon;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scorpion extends Creature implements Runnable, Summon {
    private final Block[][] battlefield;
    public Scorpion(Block[][] battlefield) {
        this.battlefield = battlefield;
        image = new Image("scorpion.png");
        good = false;
        direction = -1;
        moveFinished = false;
    }
    public String toString() {
        return "蝎子";
    }
    @Override
    public Image getImage() {
        return image;
    }

    public void summon() {}
    @TODO(todo = "随机行走,目前只采取避让策略，走出界即结束。之后考虑碰撞事件")
    public void run() {
        Random random = new Random();
        //现阶段采取避让策略
        while(true) {
            int choice = random.nextInt()%4;
            int i = getPosition().getY()/70;
            int j = getPosition().getX()/70;
            synchronized (battlefield) {
                if(choice == 0 && j>0) {
                    if(battlefield[i][j-1].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i][j-1].creatureEnter(this);
                        setPosition((j-1)*70,i*70);
                        direction = -1;
                    }
                }
                else if(choice == 1 && i>0) {
                    if(battlefield[i-1][j].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i-1][j].creatureEnter(this);
                        setPosition(j*70,(i-1)*70);
                    }
                }
                else if(choice == 2 && j<17) {
                    if(battlefield[i][j+1].isEmpty()) {
                        battlefield[i][j].creatureLeave();
                        battlefield[i][j+1].creatureEnter(this);
                        setPosition((j+1)*70,i*70);
                        direction = 1;
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
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
