package njuczh.Formations;

import njuczh.Attributes.Position;

public class YanXing implements  FormationProvider{
    public Position[] provideFormation() {
        Position[] positions = {new Position(7*70,2*70),new Position(6*70,3*70), new Position(5*70,4*70), new Position(4*70,5*70),
                        new Position(3*70,6*70),new Position(2*70,7*70),new Position(70,8*70),new Position(0,9*70)};
        return positions;
    }
    public String getName() {
        return "雁行阵！";
    }
}
