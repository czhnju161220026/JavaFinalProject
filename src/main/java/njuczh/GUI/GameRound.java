package njuczh.GUI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import njuczh.Attributes.Position;
import njuczh.Battle.Battlefield;
import njuczh.Battle.Evildoers;
import njuczh.Battle.Heroes;
import njuczh.MyAnnotation.*;
import njuczh.Things.Bullet;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//一回合游戏
@Author
public class GameRound implements Runnable{
    private Heroes heroes;
    private Evildoers evildoers;
    private Battlefield battlefield;
    private ArrayList<Bullet> bullets;
    private GraphicsContext gc;
    private TextArea textArea;
    private Image background= new Image("background.png");
    private boolean isGamming = true;
    private ExecutorService bulletExcutor;

    public GameRound(Heroes heroes,Evildoers evildoers,Battlefield battlefield,GraphicsContext gc,TextArea textArea) {
        this.heroes = heroes;
        this.evildoers = evildoers;
        this.battlefield = battlefield;
        this.gc = gc;
        this.textArea = textArea;
        bullets = new ArrayList<Bullet>();
        bulletExcutor = Executors.newCachedThreadPool();
    }

    private void displayAll() {
        while(isGamming) {
            try {
                gc.drawImage(background,0,0,1260,711);
                battlefield.displayBattlefield(gc);
                for(Bullet bullet:bullets) {
                    bullet.display(gc);
                }
                TimeUnit.MILLISECONDS.sleep(20);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TODO(todo="进行线程的分配，游戏进行，游戏战斗的记录")
    public void run(){
        System.out.println("开始新回合");
        Bullet bullet1 = new Bullet(" ",true,new Position(100,100),1);
        Bullet bullet2 = new Bullet(" ",false,new Position(90,90),1);
        bullets.add(bullet1);
        bullets.add(bullet2);
        bulletExcutor.execute(bullet1);
        bulletExcutor.execute(bullet2);
        displayAll();
        System.out.println("Round done");
    }

    @TODO(todo="完成一些游戏结束后的清理")
    public void endGame() {
        isGamming = false;
        bulletExcutor.shutdown();
    }
}
