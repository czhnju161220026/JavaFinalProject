package njuczh.Battle;

import njuczh.Attributes.Color;
import java.util.ArrayList;
import java.util.Arrays;

import njuczh.Attributes.Position;
import njuczh.Formations.FormationProvider;
import njuczh.Things.*;

public class Heroes {
    private ArrayList<Creature> heros;
    public Heroes() {
        heros = new ArrayList<Creature>();
        heros.addAll(Arrays.asList(new CalabashBrother(Color.RED),new CalabashBrother(Color.ORANGE),
                new CalabashBrother(Color.YELLOW), new CalabashBrother(Color.GREEN),new CalabashBrother(Color.BLUE),
                new CalabashBrother(Color.CYAN),new CalabashBrother(Color.PURPLE)));
        heros.add(new Grandfather());
    }

    public String changeFormation(FormationProvider fp, Block[][] battlefield) {
        String formationName = fp.getName();
        System.out.println("Heroes:"+formationName);
        Position[] positions = fp.provideFormation();
        for(Creature hero:heros) {
            hero.setPosition(positions[heros.indexOf(hero)].getX(),positions[heros.indexOf(hero)].getY());
        }

        for(Creature hero:heros) {
            Position pos = hero.getPosition();
            battlefield[pos.getX()][pos.getY()].creatureEnter(hero);
        }
        return formationName;
    }
}
