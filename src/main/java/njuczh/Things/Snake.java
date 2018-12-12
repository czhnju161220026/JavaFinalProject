package njuczh.Things;

import njuczh.Attributes.Position;
import njuczh.Battle.Block;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.Cure;
import javafx.scene.image.Image;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Snake extends Creature implements Runnable, Cure {
    private final Block[][] battlefield;
    public Snake(Block[][] battlefield) {
        this.battlefield = battlefield;
        image = new Image("snake.png");
        good = false;
        moveFinished = false;
        helth = 2;
        maxHelth = 10;
    }
    public String toString() {
        return "蛇精";
    }
    @Override
    public Image getImage() {
        return image;
    }

    public void cheer() {}
    @TODO(todo = "随机行走,目前只采取避让策略，走出界即结束。之后考虑碰撞事件")
    public void run() {
        Random random = new Random();
        //现阶段采取避让策略
        while(helth!=0) {
            int choice = random.nextInt()%4;
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
                else if(choice == 2 && j<17) {
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
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
