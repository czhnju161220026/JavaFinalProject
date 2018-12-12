package njuczh.Things;
import njuczh.Battle.Block;
import njuczh.MyAnnotation.TODO;
import njuczh.Skills.*;
import javafx.scene.image.Image;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Grandfather extends Creature implements Cure,Runnable{
    private final Block[][] battlefield;
    public Grandfather(Block[][] battlefield) {
        this.battlefield = battlefield;
        image = new Image("grandfather.png");
        attackPower = 0;
        denfensePower = 0;
        health = 200;
        maxHelth = 200;
        good = true;
        moveFinished = false;
    }

    @Override
    public Image getImage() {
        return image;
    }
    @Override
    public String toString() {
        return "爷爷";
    }
    public void cheer(){}
    @TODO(todo = "随机行走,目前只采取避让策略，走出界即结束。之后考虑碰撞事件")
    public void run() {
        Random random = new Random();
        //现阶段采取避让策略
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
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
