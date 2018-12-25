package njuczh.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import njuczh.MyAnnotation.TODO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.util.concurrent.TimeUnit;

@TODO(todo = "解析日志内容，将日志内容中的路径信息加载到各个生物中。")
public class GameReview implements Runnable{
    private static final Image cb1 = new Image("1.png");
    private static final Image cb2 = new Image("2.png");
    private static final Image cb3 = new Image("3.png");
    private static final Image cb4 = new Image("4.png");
    private static final Image cb5 = new Image("5.png");
    private static final Image cb6 = new Image("6.png");
    private static final Image cb7 = new Image("7.png");
    private static final Image[] cbs = {cb1,cb2,cb3,cb4,cb5,cb6,cb7};
    private static final Image bullet = new Image("bullet.png");
    private static final Image bullet2 = new Image("bullet2.png");
    private static final Image water = new Image("water.png");
    private static final Image stinger = new Image("stinger.png");
    private static final Image fire = new Image("fire.png");
    private static final Image[] bullets = {bullet,bullet2,fire,water,stinger};
    private static final Image dead = new Image("dead.png");
    private static final Image failed = new Image("failed.png");
    private static final Image grandfather = new Image("grandfather.png");
    private static final Image monster = new Image("monster.png");
    private static final Image scorpion = new Image("scorpion.png");
    private static final Image snake = new Image("snake.png");
    private static final Image victory = new Image("victory.png");
    private static final Image explode = new Image("explode.png");


    private List<GameFrame> frameList = new ArrayList<>();
    private GraphicsContext gc;
    private TextArea textArea;
    private boolean isReviewing = true;

    public GameReview(File log, GraphicsContext gc, TextArea textArea) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(log), StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(reader);
            this.gc = gc;
            this.textArea = textArea;
            GameFrame frame = new GameFrame();
            String head;
            while(scanner.hasNextLine()) {
                head = scanner.next();
                if(head.equals("##")) {
                    //new frame
                    frameList.add(frame);
                    frame = new GameFrame();
                }

                else if(head.equals("Win")) {
                    frame.addElement(new GameElement(victory,420,202,522,316));
                    frameList.add(frame);
                    frame = new GameFrame();
                }

                else if(head.equals("Fail")) {
                    frame.addElement(new GameElement(failed,420,202,522,316));
                    frameList.add(frame);
                    frame = new GameFrame();
                }

                else if(head.equals("C")) {
                    int index = scanner.nextInt();
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    float ratio = scanner.nextFloat();
                    int isCured = scanner.nextInt();
                    frame.addElement(new GameElement(cbs[index],x,y,72,72,ratio,isCured));
                }

                else if(head.equals("B")) {
                    int index = scanner.nextInt();
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    if(index > 1) {
                        frame.addElement(new GameElement(bullets[index],x,y+15,40,20));
                    }
                    else {
                        frame.addElement(new GameElement(bullets[index],x,y+31,10,10));
                    }
                }

                else if(head.equals("M")) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    float ratio = scanner.nextFloat();
                    int isCured = scanner.nextInt();
                    frame.addElement(new GameElement(monster,x,y,72,72,ratio,isCured));
                }

                else if(head.equals("SC")) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    float ratio = scanner.nextFloat();
                    int isCured = scanner.nextInt();
                    frame.addElement(new GameElement(scorpion,x,y,72,72,ratio,isCured));
                }

                else if(head.equals("SN")) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    float ratio = scanner.nextFloat();
                    frame.addElement(new GameElement(snake,x,y,72,72,ratio,0));
                }

                else if(head.equals("G")) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    float ratio = scanner.nextFloat();
                    frame.addElement(new GameElement(grandfather,x,y,72,72,ratio,0));
                }

                else if(head.equals("T")) {
                    frame.addText(scanner.next());
                }

                else if(head.equals("D")) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    frame.addElement(new GameElement(dead,x,y,72,72));
                }

                else if(head.equals("E")) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    frame.addElement(new GameElement(explode,x,y,40,40));
                }
                else {
                    System.out.println(head);
                    frame.addText(head);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        for(GameFrame frame : frameList) {
            frame.displayFrame(gc,textArea);
            gc.setStroke(Color.WHITE);
            gc.strokeText("FPS: "+20,5,30); //绘制帧数
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!isReviewing) {
                break;
            }
        }
        textArea.appendText("回放结束，请按结束回放按钮");
    }

    public void endReview() {
        isReviewing = false;
    }

}
