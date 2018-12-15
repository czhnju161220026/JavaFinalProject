package njuczh;
import njuczh.Attributes.Color;
import njuczh.Battle.CreaturesMeet;
import njuczh.Things.CalabashBrother;
import njuczh.Things.Monster;
import org.junit.*;
public class TestCreaturesMeet {


    @Test
    public void testCreaturesMeet1() {
        try {
            CalabashBrother cb = new CalabashBrother(Color.RED);
            Monster monster = new Monster();
            long start = System.currentTimeMillis();
            CreaturesMeet fight = new CreaturesMeet(cb,monster);
            long end = System.currentTimeMillis();
            Assert.assertTrue((end-start) < 100);
        }
        catch (RuntimeException e) {
            ;
        }
    }

    @Test
    public void testCreaturesMeet2() {
        try {
            CalabashBrother cb = new CalabashBrother(Color.RED);
            Monster monster = new Monster();
            CreaturesMeet fight = new CreaturesMeet(cb,monster);
            Assert.assertTrue(monster.isDead());
        }
        catch (RuntimeException e) {
            ;
        }
    }
}
