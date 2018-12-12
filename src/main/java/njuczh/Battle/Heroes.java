package njuczh.Battle;

import njuczh.Attributes.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import njuczh.Attributes.Position;
import njuczh.Formations.FormationProvider;
import njuczh.MyAnnotation.Author;
import njuczh.Things.*;

@Author(name = "崔子寒")
public class Heroes {

    private ArrayList<CalabashBrother>calabashBrothers;
    private Grandfather grandfather;

    public Heroes(Block[][] battlefield) {
        calabashBrothers = new ArrayList<CalabashBrother>();
        calabashBrothers.addAll(Arrays.asList(
                new CalabashBrother(Color.RED,battlefield),
                new CalabashBrother(Color.ORANGE,battlefield),
                new CalabashBrother(Color.YELLOW,battlefield),
                new CalabashBrother(Color.GREEN,battlefield),
                new CalabashBrother(Color.BLUE,battlefield),
                new CalabashBrother(Color.CYAN,battlefield),
                new CalabashBrother(Color.PURPLE,battlefield)));
        grandfather = new Grandfather(battlefield);
    }

    public String changeFormation(FormationProvider fp, Block[][] battlefield) {
        String formationName = fp.getName();
        Position[] positions = fp.provideFormation();
        for(CalabashBrother calabashBrother:calabashBrothers) {
            calabashBrother.setPosition(positions[calabashBrothers.indexOf(calabashBrother)].getX(),positions[calabashBrothers.indexOf(calabashBrother)].getY());
        }
        grandfather.setPosition(positions[7].getX(),positions[7].getY());

        for(CalabashBrother hero:calabashBrothers) {
            Position pos = hero.getPosition();
            battlefield[pos.getY()/72][pos.getX()/72].creatureEnter(hero);
        }
        Position pos = grandfather .getPosition();
        battlefield[pos.getY()/72][pos.getX()/72].creatureEnter(grandfather);
        return formationName;
    }

    public ArrayList<CalabashBrother> getCalabashBrothers() {
        return calabashBrothers;
    }

    public Grandfather getGrandfather() {
        return grandfather;
    }

}
