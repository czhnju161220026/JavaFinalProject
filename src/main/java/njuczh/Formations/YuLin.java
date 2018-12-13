package njuczh.Formations;

import njuczh.Attributes.Position;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class YuLin implements FormationProvider{
    public Position[] provideFormation() {
        Position[] positions = {new Position(4*72,3*72),new Position(4*72,4*72),new Position(5*72,4*72), new Position(3*72,5*72),
                new Position(4*72,5*72),new Position(5*72,5*72),new Position(6*72,5*72),new Position(4*72,6*72)};
        return positions;
    }

    public String getName() {
        return "鱼鳞阵！";
    }
}
