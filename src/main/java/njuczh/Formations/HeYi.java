package njuczh.Formations;
import njuczh.Attributes.Position;

public class HeYi implements FormationProvider{
    private  static Position[] positions = {new Position(0,2*70),new Position(1*70,3*70),new Position(2*70,4*70), new Position(3*70,5*70),
            new Position(4*70,5*70),new Position(5*70,4*70),new Position(6*70,3*70),new Position(7*70,2*70)};
    public Position[] provideFormation() {
        return positions;
    }

    public String getName() {
        return "鹤翼阵！";
    }
}
