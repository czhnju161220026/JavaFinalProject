package njuczh.Things;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import njuczh.Attributes.Position;
import njuczh.MyAnnotation.TODO;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class Bullet extends Thing implements Runnable{
    private Image image;//攻击
    private int attackPower;//射击者名字
    private String shooterName;//来自的阵营
    private boolean good;
    private Position pos;
    private int vector;
    public Bullet(String shooterName,boolean good,Position pos,int vector) {
        this.shooterName = shooterName;
        this.good = good;
        this.pos = pos;
        this.vector = vector;
        if(good) {
            image = new Image("bullet.png");
        }
        else {
            image = new Image("bullet2.png");
        }
    }
    @TODO(todo = "在图像上绘制自己")
    public void display(GraphicsContext gc) {
        gc.drawImage(image,pos.getX(),pos.getY(),10,10);
    }
    @TODO(todo = "沿着vector指示的方向移动，检测撞击事件")
    public void run() {
        while(pos.getX() > 0 && pos.getX() < 1260 ) {
            pos.setX(pos.getX()+vector*20);
            try{
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Bullet done");
    }
}
