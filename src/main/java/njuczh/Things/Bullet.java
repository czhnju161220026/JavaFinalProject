package njuczh.Things;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.Position;
import njuczh.MyAnnotation.TODO;

import java.util.concurrent.TimeUnit;

public class Bullet extends Thing implements Runnable{
    private Image image;//攻击
    private int attackPower;
    private String shooterName;
    private boolean good;
    private Position pos;
    private int direction;
    private boolean isDone = false;
    private BulletAttribute attribute;
    public Bullet(String shooterName, BulletAttribute attribute, Position pos) {
        this.shooterName = shooterName;
        this.pos = pos;
        this.attribute = attribute;
        image = new Image(attribute.getImagePath());
        if(attribute==BulletAttribute.EVIL) {
            good = false;
            direction = -1;
        }
        else {
            good = true;
            direction = 1;
        }
    }
    @TODO(todo = "在图像上绘制自己")
    public void display(GraphicsContext gc) {
        if(attribute == BulletAttribute.FIRE || attribute == BulletAttribute.WATER) {
            gc.drawImage(image,pos.getX(),pos.getY()+15,20,40);
        }
        else {
            gc.drawImage(image,pos.getX(),pos.getY()+30,10,10);
        }
    }

    public boolean isDone() {
        return isDone;
    }
    @TODO(todo = "沿着指示的方向移动，检测撞击事件")
    public void run() {
        while(pos.getX() > 0 && pos.getX() < 1260 ) {
            pos.setX(pos.getX()+ direction *20);
            try{
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isDone = true;
        //System.out.println("Bullet done");
    }
}
