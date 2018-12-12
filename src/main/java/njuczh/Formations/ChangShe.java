package njuczh.Formations;

import njuczh.Attributes.Position;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class ChangShe implements FormationProvider{
    public Position[] provideFormation() {
        Position[] positions ={new Position(5*70,1*70),new Position(5*70,2*70),new Position(5*70,3*70), new Position(5*70,4*70),
                new Position(5*70,5*70),new Position(5*70,6*70),new Position(5*70,7*70),new Position(5*70,8*70)};
        return positions;
    }

    public String getName() {
        return "长蛇阵！";
    }
}
