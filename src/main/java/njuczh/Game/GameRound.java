package njuczh.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import njuczh.Attributes.Position;
import njuczh.Battle.*;
import njuczh.MyAnnotation.*;
import njuczh.Things.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

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
    private Image victory = new Image("victory.png");
    private Image failed = new Image("failed.png");
    private BufferedWriter logWriter;
    private boolean isGamming = true;
    private ExecutorService heroBulletExecutor;
    private ExecutorService evilBulletExecutor;
    private ExecutorService creatureExecutor;


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
        hitQueue = new LinkedList<>();
        meetQueue = new LinkedList<>();
        heroBulletExecutor = Executors.newCachedThreadPool();
        evilBulletExecutor = Executors.newCachedThreadPool();
        creatureExecutor = Executors.newCachedThreadPool();
        CalabashBrother.setBullets(heroBullets);
        CalabashBrother.setBulletExecutor(heroBulletExecutor);
        Monster.setBullets(evilBullets);
        Monster.setBulletExecutor(evilBulletExecutor);
        Bullet.setHitQueue(hitQueue);
        Creature.setMeetQueue(meetQueue);
        Creature.setBattlefield(battlefield.getBattlefield());
    }


    private void initThreads() {
        for(CalabashBrother cb:calabashBrothers) {
            creatureExecutor.execute(cb);
        }
        creatureExecutor.execute(grandfather);
        for(Monster monster:monsters) {
            creatureExecutor.execute(monster);
        }
        creatureExecutor.execute(scorpion);
        creatureExecutor.execute(snake);
        creatureExecutor.shutdown();
        Date date = new Date();
        try {
            String path = "Log_"+date.getTime()+".myLog";
            logWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
                battlefield.outputLog(logWriter);

                synchronized (heroBullets) {
                    for(Bullet bullet: heroBullets) {
                        if(!bullet.isDone()) {
                            bullet.display(gc);
                            logWriter.write(bullet.getInfo());
                            logWriter.newLine();
                        }
                    }
                }
                synchronized (evilBullets) {
                    for(Bullet bullet: evilBullets) {
                        if(!bullet.isDone()) {
                            bullet.display(gc);
                            logWriter.write(bullet.getInfo());
                            logWriter.newLine();
                        }
                    }
                }
                synchronized (hitQueue) {
                    while(!hitQueue.isEmpty()) {
                        BulletHit hit = hitQueue.poll();
                        gc.drawImage(explode,hit.getPos().getX(),hit.getPos().getY()+15,40,40);
                        logWriter.write("E "+hit.getPos().getX()+" "+(hit.getPos().getY()+15));
                        logWriter.newLine();
                        if(hit.getResult()!="") {
                            textArea.appendText(hit.getResult());
                            logWriter.write("T "+hit.getResult());
                        }
                    }
                }
                synchronized (meetQueue) {
                    while(!meetQueue.isEmpty()) {
                        CreaturesMeet meet = meetQueue.poll();
                        textArea.appendText(meet.getResult());
                        logWriter.write("T "+meet.getResult());
                    }
                }
                gc.setStroke(Color.WHITE);
                gc.strokeText("FPS: "+FPS,5,30); //绘制帧数
                logWriter.write("##");
                logWriter.newLine();
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
        try {
            if(Heroes.allDead()) {
                gc.drawImage(failed,402,202,522,316);
                logWriter.write("Fail");
            }
            else {
                gc.drawImage(victory,402,202,522,316);
                logWriter.write("Win");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @TODO(todo="退出游戏")
    public void endGame() {
        isGamming = false;
    }

    @TODO(todo="完成游戏结束后的清理")
    public void cleanUp() {
        try {
            logWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
        creatureExecutor.shutdown();
        heroBulletExecutor.shutdown();
        evilBulletExecutor.shutdown();
        //等待所有线程正常退出
        while(!creatureExecutor.isTerminated()) {}
        while(!evilBulletExecutor.isTerminated()) {}
        while(!heroBulletExecutor.isTerminated()) {}
    }

    @TODO(todo="进行线程的分配，游戏进行，游戏战斗的记录")
    public void run(){
        System.out.println("开始新回合");
        initThreads();
        displayAll();
        endGame();
        cleanUp();
        textArea.appendText("游戏结束，请按结束游戏按钮");
    }
}

