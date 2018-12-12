package njuczh.Formations;

import njuczh.Attributes.Position;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class YanYue implements FormationProvider {
    public Position[] provideFormation() {
        Position[] positions = {new Position(5*70,2*70),new Position(4*70,3*70),new Position(3*70,4*70), new Position(4*70,4*70),
                new Position(3*70,5*70),new Position(4*70,5*70),new Position(4*70,6*70),new Position(5*70,7*70)};
        return positions;
    }
    public String getName() {
        return "偃月阵！";
    }
}
