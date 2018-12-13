package njuczh.Formations;
import njuczh.Attributes.Position;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class HeYi implements FormationProvider{
    private  static Position[] positions = {new Position(0,2*72),new Position(1*72,3*72),new Position(2*72,4*72), new Position(3*72,5*72),
            new Position(4*72,5*72),new Position(5*72,4*72),new Position(6*72,3*72),new Position(7*72,2*72)};
    public Position[] provideFormation() {
        return positions;
    }

    public String getName() {
        return "鹤翼阵！";
    }
}
