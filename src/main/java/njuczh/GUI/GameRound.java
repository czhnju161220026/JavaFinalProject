package njuczh.GUI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import njuczh.Battle.Battlefield;
import njuczh.Battle.Evildoers;
import njuczh.Battle.Heroes;
import njuczh.MyAnnotation.*;
import njuczh.Things.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

//一回合游戏
@Author
public class GameRound implements Runnable{
    private ArrayList<CalabashBrother> calabashBrothers;
    private Grandfather grandfather;
    private ArrayList<Monster> monsters;
    private Snake snake;
    private Scorpion scorpion;
    private Battlefield battlefield;
    private ArrayList<Bullet> heroBullets;
    private ArrayList<Bullet> evilBullets;
    private GraphicsContext gc;
    private static TextArea textArea = null;
    private Image background= new Image("background.png");
    private boolean isGamming = true;
    private ExecutorService heroBulletExecutor;
    private ExecutorService evilBulletExecutor;
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
        heroBullets = new ArrayList<Bullet>();
        evilBullets = new ArrayList<Bullet>();
        heroBulletExecutor = Executors.newCachedThreadPool();
        evilBulletExecutor = Executors.newCachedThreadPool();
        creatureExcutor = Executors.newCachedThreadPool();
        CalabashBrother.setBullets(heroBullets);
        CalabashBrother.setBulletExecutor(heroBulletExecutor);
        Monster.setBullets(evilBullets);
        Monster.setBulletExecutor(evilBulletExecutor);


    }
    public static TextArea getGameLog() {
        return textArea;
    }
    private void displayAll() {
        int count = 0;
        long startTime = 0;
        long endTime = 0;
        int FPS = 0;
        while(isGamming) {
            try {
                startTime = System.currentTimeMillis();
                gc.drawImage(background,0,0,1296,721);
                battlefield.displayBattlefield(gc);
                synchronized (heroBullets) {
                    for(Bullet bullet: heroBullets) {
                        if(!bullet.isDone()) {
                            bullet.display(gc);
                        }
                    }
                }
                synchronized (evilBullets) {
                    for(Bullet bullet: evilBullets) {
                        if(!bullet.isDone()) {
                            bullet.display(gc);
                        }
                    }
                }
                gc.setStroke(Color.WHITE);
                gc.strokeText("FPS: "+FPS,5,30); //绘制帧数
                TimeUnit.MILLISECONDS.sleep(25);
                endTime = System.currentTimeMillis();
                FPS = (int)(1000/(endTime-startTime));  //计算瞬时帧数
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                //清理射出屏幕和已经命中的子弹
                synchronized (heroBullets) {
                    heroBullets.removeIf(new Predicate<Bullet>() {
                        @Override
                        public boolean test(Bullet bullet) {
                            return bullet.isDone();
                        }
                    });
                }

                synchronized (evilBullets) {
                    evilBullets.removeIf(new Predicate<Bullet>() {
                        @Override
                        public boolean test(Bullet bullet) {
                            return bullet.isDone();
                        }
                    });
                }

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
        //heroBulletExecutor.shutdown();
        //杀死所有生物，以结束它们的线程
        for(CalabashBrother cb: calabashBrothers) {
            cb.kill();
        }
        for(Monster monster:monsters) {
            monster.kill();
        }
        for(Bullet bullet:evilBullets) {
            bullet.setDone();
        }
        for(Bullet bullet:heroBullets) {
            bullet.setDone();
        }
        grandfather.kill();
        snake.kill();
        scorpion.kill();
        creatureExcutor.shutdown();
        heroBulletExecutor.shutdown();
        evilBulletExecutor.shutdown();
        while(!creatureExcutor.isTerminated()) {}
        while(!evilBulletExecutor.isTerminated()) {}
        while(!heroBulletExecutor.isTerminated()) {}

    }
}
