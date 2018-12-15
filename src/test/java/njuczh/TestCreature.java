package njuczh;

import njuczh.Attributes.Color;
import njuczh.Attributes.Position;
import njuczh.Battle.Battlefield;
import njuczh.Things.CalabashBrother;
import njuczh.Things.Creature;
import njuczh.Things.Monster;
import org.junit.*;

public class TestCreature {
    @Test
    public void testMoveTo() throws Exception{
        try {
            Battlefield battlefield = new Battlefield();
            Creature.setBattlefield(battlefield.getBattlefield());
            CalabashBrother cb = new CalabashBrother(Color.RED);
            Position old = new Position();
            Position target = new Position();
            old.setI(0);
            old.setJ(1);
            target.setI(5);
            target.setJ(5);
            cb.setPosition(old.getX(),old.getY());
            cb.moveTo(target);
            Assert.assertEquals(battlefield.getBattlefield()[5][5].getCreature(),cb);
        }
        catch (RuntimeException e) {
            ;//do nothing
        }

    }

    @Test
    public void testIsDead() {
        try {
            Monster monster = new Monster();
            monster.setHealth(10);
            Assert.assertEquals(false,monster.isDead());
            monster.setHealth(0);
            Assert.assertEquals(true,monster.isDead());
        }
        catch (RuntimeException e) {
            ;//do nothing
        }
    }
}
