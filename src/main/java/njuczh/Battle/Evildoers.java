package njuczh.Battle;

import njuczh.Attributes.Position;
import njuczh.Formations.FormationProvider;
import njuczh.MyAnnotation.Author;
import njuczh.Things.Creature;
import njuczh.Things.Monster;
import njuczh.Things.Scorpion;
import njuczh.Things.Snake;

import java.util.ArrayList;

@Author(name = "崔子寒")
public class Evildoers {
    Snake snake ;
    Scorpion scorpion ;
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    private static int aliveCount=10;
    public Evildoers() {
        aliveCount = 10;
        for(int i = 0;i < 8;i++) {
            monsters.add(new Monster());
        }
        snake = new Snake();
        scorpion = new Scorpion();
        snake.setPosition(17*72,3*72);
        scorpion.setPosition(17*72,5*72);
    }
    public static  boolean allDead() {
        synchronized (Evildoers.class) {
            return aliveCount == 0;
        }
    }
    public static void evildoerDie() {
        synchronized (Evildoers.class) {
            aliveCount--;
        }
    }

    public String changeFormation(FormationProvider fp, Block[][] battlefield) {
        String formationName = fp.getName();
        Position[] positions = fp.provideFormation();
        for(Creature badGuy: monsters) {
            badGuy.setPosition(1224-positions[monsters.indexOf(badGuy)].getX(),positions[monsters.indexOf(badGuy)].getY());
        }

        for(Creature badGuy: monsters) {
            Position pos = badGuy.getPosition();
            battlefield[pos.getY()/72][pos.getX()/72].creatureEnter(badGuy);
        }
        battlefield[3][17].creatureEnter(snake);
        battlefield[5][17].creatureEnter(scorpion);
        return formationName;
    }

    public Snake getSnake() {
        return snake;
    }

    public Scorpion getScorpion() {
        return scorpion;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
