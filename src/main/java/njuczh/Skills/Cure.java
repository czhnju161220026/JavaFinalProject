package njuczh.Skills;

import njuczh.MyAnnotation.Author;

//爷爷和蛇精可以缓慢恢复周围友军的血量
@Author(name = "崔子寒")
public interface Cure {
    void cheer();//设置周围友军的恢复状态
}
