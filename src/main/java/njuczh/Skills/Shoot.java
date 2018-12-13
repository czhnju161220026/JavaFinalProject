package njuczh.Skills;

import njuczh.MyAnnotation.Author;
import njuczh.Things.Bullet;

//葫芦娃和小兵可以射击
@Author(name = "崔子寒")
public interface Shoot {
    Bullet shoot();//周期性产生子弹
}
