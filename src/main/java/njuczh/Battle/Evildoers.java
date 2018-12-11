package njuczh.Battle;

import njuczh.Attributes.Position;
import njuczh.Formations.FormationProvider;
import njuczh.Things.Creature;
import njuczh.Things.Monster;
import njuczh.Things.Scorpion;
import njuczh.Things.Snake;

import java.util.ArrayList;

public class Evildoers {
    private ArrayList<Creature> evildoers = new ArrayList<Creature>();
    public Evildoers() {
        evildoers.add(new Snake());
        evildoers.add(new Scorpion());
        for(int i = 0;i < 6;i++) {
            evildoers.add(new Monster());
        }
    }

    public String changeFormation(FormationProvider fp, Block[][] battlefield) {
        String formationName = fp.getName();
        Position[] positions = fp.provideFormation();
        for(Creature badGuy:evildoers) {
            badGuy.setPosition(positions[evildoers.indexOf(badGuy)].getX(),17-positions[evildoers.indexOf(badGuy)].getY());
        }

        for(Creature badGuy:evildoers) {
            Position pos = badGuy.getPosition();
            battlefield[pos.getX()][pos.getY()].creatureEnter(badGuy);
        }
        return formationName;
    }
}
