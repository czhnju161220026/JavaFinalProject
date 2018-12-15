package njuczh.Attributes;

import njuczh.MyAnnotation.Author;

/*不同的颜色对应不同的能力
* 大娃：近战攻击力高,血量多
* 二娃：远程攻击力高但是防御力低
* 三娃：防御力高
* 四娃，五娃：远程攻击力高
* 六娃：免疫远程攻击
* 七娃：对蛇精和蝎子精具有很高伤害
* */
@Author(name = "崔子寒")
public enum Color {
    RED("大娃",60,50,500),
    ORANGE("二娃",40,40,300),
    YELLOW("三娃",50,70,300),
    GREEN("四娃",50,50,300),
    CYAN("五娃",50,50,300),
    BLUE("六娃",40,40,300),
    PURPLE("七娃",55,40,300);

    final private  String name;
    final private int attackPower;
    final private int defensePower;
    final private int maxHelth;
    Color(String name,int attackPower,int defensePower,int maxHelth) {
        this.name = name;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.maxHelth = maxHelth;
    }

    public String getName() {
        return this.name;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public int getMaxHelth() {
        return maxHelth;
    }
}
