package njuczh.Formations;
import njuczh.Attributes.*;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public interface FormationProvider {
    Position[] provideFormation();
    String getName();
}
