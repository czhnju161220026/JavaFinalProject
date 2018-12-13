package njuczh.Battle;

import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.MyAnnotation.TODO;
import njuczh.Things.Creature;
import njuczh.Things.DeadCreature;

@TODO(todo="两个不同阵营的生物相遇的事件")
public class CreaturesMeet {
    private String result;
    public CreaturesMeet(Creature creature1,Creature creature2) {
        StringBuilder stringBuilder =new StringBuilder("");
        int attackPower1 = creature1.getAttackPower();
        int attackPower2 = creature2.getAttackPower();
        int defensePower1 = creature1.getDenfensePower();
        int defensePower2 = creature2.getDenfensePower();
        int helth1 = creature1.getHealth();
        int helth2 = creature2.getHealth();
        int harm2 = attackPower1 - defensePower1;
        int harm1 = attackPower2 - defensePower2;
        boolean flag = false;
        DeadCreature dead = new DeadCreature();
        if(harm1 <= 0) {
            harm1 = 5;
        }
        if(harm2 <= 0) {
            harm2 = 5;
        }

        while(helth1 > 0 && helth2 > 0) {
            helth1-=harm1;
            helth2-=harm2;
        }
        if(helth1<=0 && helth2<=0) {
            stringBuilder.append(creature2+"对"+creature1+"造成了"+(creature1.getHealth()-10)+"点伤害。\n");
            stringBuilder.append(creature1+"击杀了"+creature2+"\n");
            creature1.setHealth(10);
            creature2.die();
            if(creature2.getProperty()== CreatureAttribute.GOOD) {
                Heroes.heroDie();
            }
            else {
                Evildoers.evildoerDie();
            }
        }
        else if(helth1 <=0) {
            stringBuilder.append(creature1+"对"+creature2+"造成了"+(creature2.getHealth()-helth2)+"点伤害。\n");
            stringBuilder.append(creature2+"击杀了"+creature1+"\n");
            creature1.die();
            creature2.setHealth(helth2);
            if(creature1.getProperty()==CreatureAttribute.GOOD) {
                Heroes.heroDie();
            }
            else {
                Evildoers.evildoerDie();
            }
        }
        else {
            stringBuilder.append(creature2+"对"+creature1+"造成了"+(creature1.getHealth()-helth1)+"点伤害。\n");
            stringBuilder.append(creature1+"击杀了"+creature2+"\n");
            creature2.die();
            creature1.setHealth(helth1);
            if(creature2.getProperty()==CreatureAttribute.GOOD) {
                Heroes.heroDie();
            }
            else {
                Evildoers.evildoerDie();
            }
        }
        result = stringBuilder.toString();
    }
    @TODO(todo="返回对战结果的文字描述")
    public String getResult() {
        return result;
    }
}
