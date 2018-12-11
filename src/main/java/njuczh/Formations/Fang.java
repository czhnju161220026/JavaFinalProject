package njuczh.Formations;

import njuczh.Attributes.Position;

public class Fang implements FormationProvider {
    public Position[] provideFormation() {
        Position[] positions = {new Position(4*70,3*70),new Position(3*70,4*70),new Position(5*70,4*70), new Position(2*70,5*70),
                new Position(6*70,5*70),new Position(3*70,6*70),new Position(5*70,6*70),new Position(4*70,7*70)};
        return positions;
    }

    public String getName() {
        return "方块阵！";
    }
}
