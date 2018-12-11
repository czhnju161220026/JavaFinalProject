package njuczh.Battle;

import njuczh.Attributes.Position;
import njuczh.Formations.FormationProvider;
import njuczh.Things.Creature;
import njuczh.Things.Monster;
import njuczh.Things.Scorpion;
import njuczh.Things.Snake;

import java.util.ArrayList;

public class Evildoers {
    Snake snake ;
    Scorpion scorpion ;
    private ArrayList<Monster> monsters = new ArrayList<Monster>();

    public Evildoers(Block[][] battlefield) {
        for(int i = 0;i < 8;i++) {
            monsters.add(new Monster(battlefield));
        }
        snake = new Snake(battlefield);
        scorpion = new Scorpion(battlefield);
        snake.setPosition(17*70,3*70);
        scorpion.setPosition(17*70,5*70);
    }

    public String changeFormation(FormationProvider fp, Block[][] battlefield) {
        String formationName = fp.getName();
        Position[] positions = fp.provideFormation();
        for(Creature badGuy: monsters) {
            badGuy.setPosition(1190-positions[monsters.indexOf(badGuy)].getX(),positions[monsters.indexOf(badGuy)].getY());
        }

        for(Creature badGuy: monsters) {
            Position pos = badGuy.getPosition();
            battlefield[pos.getY()/70][pos.getX()/70].creatureEnter(badGuy);
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
