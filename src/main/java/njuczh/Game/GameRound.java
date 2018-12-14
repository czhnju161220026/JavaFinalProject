package njuczh.Game;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import njuczh.Attributes.Position;
import njuczh.Battle.*;
import njuczh.MyAnnotation.*;
import njuczh.Things.*;
import sun.misc.Queue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private Queue<BulletHit> hitQueue;
    private Queue<CreaturesMeet> meetQueue;
    private GraphicsContext gc;
    private TextArea textArea = null;
    private Image background= new Image("background.png");
    private Image explode = new Image("explode.png");
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
        hitQueue = new Queue<>();
        meetQueue = new Queue<>();
        heroBulletExecutor = Executors.newCachedThreadPool();
        evilBulletExecutor = Executors.newCachedThreadPool();
        creatureExcutor = Executors.newCachedThreadPool();
        CalabashBrother.setBullets(heroBullets);
        CalabashBrother.setBulletExecutor(heroBulletExecutor);
        Monster.setBullets(evilBullets);
        Monster.setBulletExecutor(evilBulletExecutor);
        Bullet.setHitQueue(hitQueue);
        Creature.setMeetQueue(meetQueue);
    }

    public Queue<BulletHit> getHitQueue() {
        return hitQueue;
    }

    private void initThreads() {
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
    }

    private void displayAll() {
        int count = 0;
        long startTime = 0;
        long endTime = 0;
        int FPS = 0;
        while(isGamming) {
            if(Heroes.allDead()) {
                textArea.appendText("妖怪赢得胜利。\n");
                isGamming = false;
            }
            if(Evildoers.allDead()) {
                textArea.appendText("葫芦娃赢得胜利。\n");
                isGamming = false;
            }
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
                synchronized (hitQueue) {
                    while(!hitQueue.isEmpty()) {
                        BulletHit hit = hitQueue.dequeue();
                        gc.drawImage(explode,hit.getPos().getX(),hit.getPos().getY()+15,40,40);
                        if(hit.getResult()!="") {
                            textArea.appendText(hit.getResult());
                        }
                    }
                }
                synchronized (meetQueue) {
                    while(!meetQueue.isEmpty()) {
                        CreaturesMeet meet = meetQueue.dequeue();
                        textArea.appendText(meet.getResult());
                    }
                }
                gc.setStroke(Color.WHITE);
                gc.strokeText("FPS: "+FPS,5,30); //绘制帧数
                TimeUnit.MILLISECONDS.sleep(50);
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
        endGame();
    }

    @TODO(todo = "输出每个生物的行进轨迹到日志")
    private void outputLog() {
        try{
            Date current = new Date();
            BufferedWriter fout = new BufferedWriter(new FileWriter(new File("Log_"+current.getTime()+".myLog")));
            for(CalabashBrother cb:calabashBrothers) {
                fout.write(""+cb.getTrace().size()+'\n');
                for(Position pos:cb.getTrace()) {
                    fout.write(pos+"  ");
                }
                fout.write('\n');
            }
            fout.write(""+grandfather.getTrace().size()+'\n');
            for(Position pos:grandfather.getTrace()) {
                fout.write(pos+"  ");
            }
            fout.write('\n');
            for(Monster monster:monsters) {
                fout.write(""+monster.getTrace().size()+'\n');
                for(Position pos:monster.getTrace()) {
                    fout.write(pos+"  ");
                }
                fout.write('\n');
            }
            fout.write(""+scorpion.getTrace().size()+'\n');
            for(Position pos:scorpion.getTrace()) {
                fout.write(pos+"  ");
            }
            fout.write('\n');
            fout.write(""+snake.getTrace().size()+'\n');
            for(Position pos:snake.getTrace()) {
                fout.write(pos+"  ");
            }
            fout.write('\n');
            fout.flush();
            fout.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @TODO(todo="进行线程的分配，游戏进行，游戏战斗的记录")
    public void run(){
        System.out.println("开始新回合");
        initThreads();
        displayAll();
        outputLog();
    }

    @TODO(todo="完成一些游戏结束后的清理")
    public void endGame() {
        isGamming = false;
        //heroBulletExecutor.shutdown();
        //杀死所有生物，以结束它们的线程
        for(CalabashBrother cb: calabashBrothers) {
            cb.die();
        }
        for(Monster monster:monsters) {
            monster.die();
        }
        for(Bullet bullet:evilBullets) {
            bullet.setDone();
        }
        for(Bullet bullet:heroBullets) {
            bullet.setDone();
        }
        grandfather.die();
        snake.die();
        scorpion.die();
        creatureExcutor.shutdown();
        heroBulletExecutor.shutdown();
        evilBulletExecutor.shutdown();
        //等待所有线程正常退出
        while(!creatureExcutor.isTerminated()) {}
        while(!evilBulletExecutor.isTerminated()) {}
        while(!heroBulletExecutor.isTerminated()) {}
    }
}

