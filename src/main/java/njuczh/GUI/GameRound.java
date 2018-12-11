package njuczh.GUI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import njuczh.Battle.Battlefield;
import njuczh.Battle.Evildoers;
import njuczh.Battle.Heroes;
import njuczh.MyAnnotation.*;
import njuczh.Things.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//一回合游戏
@Author
public class GameRound implements Runnable{
    private ArrayList<CalabashBrother> calabashBrothers;
    private Grandfather grandfather;
    private ArrayList<Monster> monsters;
    private Snake snake;
    private Scorpion scorpion;
    private Battlefield battlefield;
    private ArrayList<Bullet> bullets;
    private GraphicsContext gc;
    private TextArea textArea;
    private Image background= new Image("background.png");
    private boolean isGamming = true;
    private ExecutorService bulletExcutor;
    private ExecutorService creatureExcutor;

    public GameRound(Heroes heroes,Evildoers evildoers,Battlefield battlefield,GraphicsContext gc,TextArea textArea) {
        this.calabashBrothers = heroes.getCalabashBrothers();
        this.grandfather = heroes.getGrandfather();
        this.scorpion = evildoers.getScorpion();
        this.snake = evildoers.getSnake();
        this.monsters = evildoers.getMonsters();
        this.battlefield = battlefield;
        this.gc = gc;
        this.textArea = textArea;
        bullets = new ArrayList<Bullet>();
        bulletExcutor = Executors.newCachedThreadPool();
        creatureExcutor = Executors.newCachedThreadPool();
    }

    private void displayAll() {
        int count = 0;
        while(isGamming) {
            try {
                gc.drawImage(background,0,0,1260,711);
                battlefield.displayBattlefield(gc);
                for(Bullet bullet:bullets) {
                    bullet.display(gc);
                }
                TimeUnit.MILLISECONDS.sleep(20);
                count++;
                if(count==25) {
                    count = 0;
                    for(CalabashBrother cb:calabashBrothers) {
                        cb.shoot();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TODO(todo="进行线程的分配，游戏进行，游戏战斗的记录")
    public void run(){
        System.out.println("开始新回合");
        for(CalabashBrother cb:calabashBrothers) {
            creatureExcutor.execute(cb);
        }
        creatureExcutor.execute(grandfather);
        for(Monster monster:monsters) {
            creatureExcutor.execute(monster);
        }
        creatureExcutor.execute(scorpion);
        creatureExcutor.execute(snake);
        creatureExcutor.shutdown();
        displayAll();
        System.out.println("Round done");
    }

    @TODO(todo="完成一些游戏结束后的清理")
    public void endGame() {
        isGamming = false;
        bulletExcutor.shutdown();
        creatureExcutor.shutdown();
    }
}
