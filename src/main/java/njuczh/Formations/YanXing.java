package njuczh.Formations;

import njuczh.Attributes.Position;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class YanXing implements  FormationProvider{
    public Position[] provideFormation() {
        Position[] positions = {new Position(7*72,2*72),new Position(6*72,3*72), new Position(5*72,4*72), new Position(4*72,5*72),
                        new Position(3*72,6*72),new Position(2*72,7*72),new Position(72,8*72),new Position(0,9*72)};
        return positions;
    }
    public String getName() {
        return "雁行阵！";
    }
}
