package njuczh.Attributes;

import njuczh.MyAnnotation.Author;

/*不同的颜色对应不同的能力
* 大娃：近战攻击力高
* 二娃：远程攻击力高
* 三娃：防御力高
* 四娃，五娃：远程攻击力高
* 六娃：免疫远程攻击
* 七娃：对蛇精和蝎子精具有很高伤害
* */
@Author(name = "崔子寒")
public enum Color {
    RED("大娃"), ORANGE("二娃"), YELLOW("三娃"), GREEN("四娃"),
    CYAN("五娃"), BLUE("六娃"), PURPLE("七娃");

    final private  String name;
    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
