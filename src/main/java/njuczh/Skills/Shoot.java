package njuczh.Skills;

import njuczh.MyAnnotation.Author;

//葫芦娃和小兵可以射击
@Author(name = "崔子寒")
public interface Shoot {
    void shoot();//周期性产生子弹
}
