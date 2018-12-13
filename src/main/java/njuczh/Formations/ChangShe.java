package njuczh.Formations;

import njuczh.Attributes.Position;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class ChangShe implements FormationProvider{
    public Position[] provideFormation() {
        Position[] positions ={new Position(5*72,1*72),new Position(5*72,2*72),new Position(5*72,3*72), new Position(5*72,4*72),
                new Position(5*72,5*72),new Position(5*72,6*72),new Position(5*72,7*72),new Position(5*72,8*72)};
        return positions;
    }

    public String getName() {
        return "长蛇阵！";
    }
}
