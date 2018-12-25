package njuczh.Battle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import njuczh.Attributes.Position;
import njuczh.MyAnnotation.Author;
import njuczh.Things.Creature;
import njuczh.Things.DeadCreature;

import java.io.BufferedWriter;
import java.io.IOException;


@Author(name = "崔子寒")
public class Battlefield {
    private Block[][] battlefield= new Block[10][18];
    private Image cure = new Image("cure.png");
    public Battlefield() {
        for(int i = 0;i < 10;i++) {
            for(int j = 0;j < 18;j++) {
                battlefield[i][j] = new Block();
            }
        }
    }

    public Block[][] getBattlefield() {
        return battlefield;
    }

    //绘制战场
    public void displayBattlefield(GraphicsContext gc) {
        synchronized (battlefield) {
            for(int i = 0; i < 10;i++) {
                for(int j = 0;j < 18;j++) {
                    if(!battlefield[i][j].isEmpty()) {
                        Creature creature = battlefield[i][j].getCreature();
                        Position pos = creature.getPosition();
                        gc.drawImage(battlefield[i][j].getImage(),pos.getX(),pos.getY(),72,72);
                        if(!(creature instanceof DeadCreature)) {
                            gc.setFill(Color.RED);
                            gc.fillRoundRect(pos.getX()+3,pos.getY(),66,5,10,10);
                            gc.setFill(Color.color(0.3,1.0,0.69));
                            float ratio = creature.getHelthRatio();
                            gc.fillRoundRect(pos.getX()+3,pos.getY(),66*ratio,5,10,10);
                            if(creature.isCured()) {
                                gc.drawImage(cure,pos.getX()+60,pos.getY()-10,20,20);
                            }
                        }
                        else {
                            gc.drawImage(creature.getImage(),pos.getX(),pos.getY(),72,72);
                            if(((DeadCreature)creature).isTimeout()) {
                                battlefield[i][j].creatureLeave();
                            }
                            else {
                                ((DeadCreature)creature).decreaseTime();
                            }
                        }
                    }
                }
            }
        }
    }

    //输出日志
    public void outputLog(BufferedWriter fout) {
        synchronized (battlefield) {
            for(int i = 0;i < 10;i++) {
                for(int j = 0;j < 18;j++) {
                    if(!battlefield[i][j].isEmpty()) {
                        try {
                            fout.write(battlefield[i][j].getCreature().getInfo());
                            fout.newLine();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public void clearBattlefield() {
        for(int i = 0;i < 10;i++) {
            for(int j = 0;j < 18;j++) {
                battlefield[i][j].creatureLeave();
            }
        }
    }
}
