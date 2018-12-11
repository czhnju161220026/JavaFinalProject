package njuczh.Formations;

import njuczh.Attributes.Position;

public class FengShi implements  FormationProvider{
    public Position[] provideFormation() {
        Position[] positions = {new Position(3*70,4*70),new Position(4*70,3*70),new Position(5*70,2*70), new Position(6*70,3*70),
                new Position(7*70,4*70),new Position(5*70,3*70),new Position(5*70,4*70),new Position(5*70,5*70)};
        return positions;
    }

    public String getName() {
        return "锋矢阵！";
    }
}
