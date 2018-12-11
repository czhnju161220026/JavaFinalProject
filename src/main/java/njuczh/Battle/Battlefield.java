package njuczh.Battle;

import javafx.scene.canvas.GraphicsContext;
import njuczh.Things.Creature;

public class Battlefield {
    private Block[][] battlefield= new Block[10][18];

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
        for(int i = 0; i < 10;i++) {
            for(int j = 0;j < 18;j++) {
                if(!battlefield[i][j].isEmpty()) {
                    gc.drawImage(battlefield[i][j].getImage(),j*70,i*70,70,70);
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
