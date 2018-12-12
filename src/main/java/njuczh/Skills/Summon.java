package njuczh.Skills;

import njuczh.MyAnnotation.Author;

//蝎子精可以召唤小怪
@Author(name = "崔子寒")
public interface Summon {
    void summon();//周期性召唤小怪
}
