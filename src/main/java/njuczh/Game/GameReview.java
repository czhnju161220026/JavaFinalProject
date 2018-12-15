package njuczh.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import njuczh.Attributes.Position;
import njuczh.Battle.*;
import njuczh.MyAnnotation.TODO;
import njuczh.Things.*;
import sun.misc.Queue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@TODO(todo = "解析日志内容，将日志内容中的路径信息加载到各个生物中。")
public class GameReview implements Runnable{
    private ArrayList<CalabashBrother> calabashBrothers;
    private Grandfather grandfather;
    private ArrayList<Monster> monsters;
    private Snake snake;
    private Scorpion scorpion;
    private Battlefield battlefield;
    private ArrayList<Bullet> heroBullets = new ArrayList<>();
    private ArrayList<Bullet> evilBullets = new ArrayList<>();
    private Queue<BulletHit> hitQueue = new Queue<>();
    private Queue<CreaturesMeet> meetQueue = new Queue<>();
    private GraphicsContext gc;
    private TextArea textArea = null;
    private Image background= new Image("background.png");
    private Image explode = new Image("explode.png");
    private boolean isReviewing = true;
    private ExecutorService heroBulletExecutor;
    private ExecutorService evilBulletExecutor;
    private ExecutorService creatureExecutor;

    public GameReview(File log, GraphicsContext gc, TextArea textArea) {
        this.gc = gc;
        this.textArea = textArea;
        this.heroBulletExecutor = Executors.newCachedThreadPool();
        this.evilBulletExecutor = Executors.newCachedThreadPool();
        this.creatureExecutor = Executors.newCachedThreadPool();
        this.battlefield = new Battlefield();
        Heroes heroes = new Heroes();
        Evildoers evildoers = new Evildoers();
        Creature.setBattlefield(battlefield.getBattlefield());
        Creature.setIsReviewing();
        CalabashBrother.setBullets(heroBullets);
        CalabashBrother.setBulletExecutor(heroBulletExecutor);
        Monster.setBullets(evilBullets);
        Monster.setBulletExecutor(evilBulletExecutor);
        Bullet.setHitQueue(hitQueue);
        Creature.setMeetQueue(meetQueue);
        calabashBrothers = heroes.getCalabashBrothers();
        grandfather = heroes.getGrandfather();
        monsters = evildoers.getMonsters();
        snake = evildoers.getSnake();
        scorpion = evildoers.getScorpion();

        Scanner fin=null;
        try {
            fin = new Scanner(new BufferedReader(new FileReader(log)));
            ArrayList<ArrayList<Position>> cbTraces = new ArrayList<>();
            ArrayList<Position> grandfatherTrace = new ArrayList<>();
            ArrayList<ArrayList<Position>> monsterTraces = new ArrayList<>();
            ArrayList<Position> snakeTrace = new ArrayList<>();
            ArrayList<Position> scorpionTrace = new ArrayList<>();

            //读取文件中所存储的生物的行进信息
            //进行复盘游戏
            for(int i = 0;i < 7;i++) {
                int count = fin.nextInt();
                ArrayList<Position> trace = new ArrayList<>();
                for(int j = 0;j<count;j++) {
                    int x = fin.nextInt();
                    int y = fin.nextInt();
                    trace.add(new Position(x,y));
                }
                cbTraces.add(trace);
            }

            int count = fin.nextInt();
            for(int i = 0;i < count;i++) {
                int x = fin.nextInt();
                int y = fin.nextInt();
                grandfatherTrace.add(new Position(x,y));
            }

            for(int i = 0;i < 8;i++) {
                count = fin.nextInt();
                ArrayList<Position> trace = new ArrayList<>();
                for(int j = 0;j < count;j++) {
                    int x = fin.nextInt();
                    int y = fin.nextInt();
                    trace.add(new Position(x,y));
                }
                monsterTraces.add(trace);
            }

            count = fin.nextInt();
            for(int i = 0;i < count;i++) {
                int x = fin.nextInt();
                int y = fin.nextInt();
                scorpionTrace.add(new Position(x,y));
            }

            count = fin.nextInt();
            for(int i = 0;i < count;i++) {
                int x = fin.nextInt();
                int y = fin.nextInt();
                snakeTrace.add(new Position(x,y));
            }

            for(int i = 0;i<7;i++) {
                calabashBrothers.get(i).setTrace(cbTraces.get(i));
            }
            grandfather.setTrace(grandfatherTrace);
            for(int i = 0;i < 8;i++) {
                monsters.get(i).setTrace(monsterTraces.get(i));
            }
            snake.setTrace(snakeTrace);
            scorpion.setTrace(scorpionTrace);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                fin.close();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
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
    }

    private void displayAll() {
        int count = 0;
        long startTime = 0;
        long endTime = 0;
        int FPS = 0;
        while(isReviewing) {
            if(Heroes.allDead()) {
                textArea.appendText("妖怪赢得胜利。\n");
                isReviewing = false;
            }
            if(Evildoers.allDead()) {
                textArea.appendText("葫芦娃赢得胜利。\n");
                isReviewing = false;
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
        endReview();
    }

    public void run() {
        while(isReviewing) {
            System.out.println("正在回放");
            initThreads();
            displayAll();
        }
    }

    public void endReview() {
        isReviewing = false;
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
}
