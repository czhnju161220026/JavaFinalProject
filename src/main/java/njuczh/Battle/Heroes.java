package njuczh.Battle;

import njuczh.Attributes.Color;
import java.util.ArrayList;
import java.util.Arrays;

import njuczh.Attributes.Position;
import njuczh.Formations.FormationProvider;
import njuczh.Things.*;

public class Heroes {

    private ArrayList<Creature> heros;

    public Heroes(Block[][] battlefield) {
        heros = new ArrayList<Creature>();
        heros.addAll(Arrays.asList(new CalabashBrother(Color.RED,battlefield),new CalabashBrother(Color.ORANGE,battlefield),
                new CalabashBrother(Color.YELLOW,battlefield), new CalabashBrother(Color.GREEN,battlefield),new CalabashBrother(Color.BLUE,battlefield),
                new CalabashBrother(Color.CYAN,battlefield),new CalabashBrother(Color.PURPLE,battlefield)));
        heros.add(new Grandfather(battlefield));
    }

    public String changeFormation(FormationProvider fp, Block[][] battlefield) {
        String formationName = fp.getName();
        Position[] positions = fp.provideFormation();
        for(Creature hero:heros) {
            hero.setPosition(positions[heros.indexOf(hero)].getX(),positions[heros.indexOf(hero)].getY());
        }

        for(Creature hero:heros) {
            Position pos = hero.getPosition();
            battlefield[pos.getY()/70][pos.getX()/70].creatureEnter(hero);
        }
        return formationName;
    }

    public ArrayList<Creature> getHeros() {
        return heros;
    }
}
