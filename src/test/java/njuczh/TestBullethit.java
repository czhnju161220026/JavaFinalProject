package njuczh;

import njuczh.Attributes.BulletAttribute;
import njuczh.Attributes.Color;
import njuczh.Battle.*;
import njuczh.Things.Bullet;
import njuczh.Things.CalabashBrother;
import njuczh.Things.Grandfather;
import org.junit.*;
public class TestBullethit {
    @Test
    public void testBullethit() {
        try {
            Bullet bullet = new Bullet("",BulletAttribute.EVIL,null,null);
            CalabashBrother cb = new CalabashBrother(Color.BLUE);
            BulletHit bullethit = new BulletHit(bullet,cb);
            Assert.assertEquals(Color.BLUE.getMaxHelth(),cb.getHealth());
        }
        catch (RuntimeException e) {
            ;
        }
    }

    @Test
    public void testBullethit2() {
        try {
            Bullet bullet = new Bullet("",BulletAttribute.STINGER,null,null);
            Grandfather grandfather = new Grandfather();
            int hitTimes = 0;
            while(!grandfather.isDead()) {
                BulletHit hit = new BulletHit(bullet,grandfather);
                hitTimes++;
            }
            Assert.assertEquals(8,hitTimes);
        }
        catch (RuntimeException e) {
            ;
        }
    }
}
