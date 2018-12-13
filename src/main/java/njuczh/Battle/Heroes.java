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
    private static int aliveCount = 8;
    public Heroes() {
        aliveCount = 8;
        calabashBrothers = new ArrayList<CalabashBrother>();
        calabashBrothers.addAll(Arrays.asList(
                new CalabashBrother(Color.RED),
                new CalabashBrother(Color.ORANGE),
                new CalabashBrother(Color.YELLOW),
                new CalabashBrother(Color.GREEN),
                new CalabashBrother(Color.BLUE),
                new CalabashBrother(Color.CYAN),
                new CalabashBrother(Color.PURPLE)));
        grandfather = new Grandfather();
    }
    public static boolean allDead() {
        synchronized (Heroes.class) {
            return aliveCount==0;
        }
    }
    public static void heroDie() {
        synchronized (Heroes.class) {
            aliveCount--;
        }
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
