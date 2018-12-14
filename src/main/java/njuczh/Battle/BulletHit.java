package njuczh.Battle;

import njuczh.Attributes.CreatureAttribute;
import njuczh.Attributes.Position;
import njuczh.MyAnnotation.TODO;
import njuczh.Things.Bullet;
import njuczh.Things.Creature;
import njuczh.Things.DeadCreature;

@TODO(todo="子弹击中某个生物的事件")
public class BulletHit {
    private String result;
    private Position pos;
    public BulletHit(Bullet bullet, Creature creature) {
        StringBuilder stringBuilder = new StringBuilder("");
        int attackPower = bullet.getAttackPower();
        String shooterName = bullet.getShooterName();
        pos = new Position(bullet.getPos().getX(),bullet.getPos().getY());
        bullet.setDone();
        int harm = attackPower-creature.getDenfensePower();
        if(harm<=0) {
            //stringBuilder.append(shooterName);
            //stringBuilder.append("的未能击穿"+creature+"的防御!\n");
            //result = stringBuilder.toString();
            result="";
        }
        else {
            stringBuilder.append(shooterName);
            int health = creature.getHealth();
            if(harm >= health) {
                creature.die();
                if(creature.getProperty()== CreatureAttribute.GOOD) {
                    Heroes.heroDie();
                }
                else {
                    Evildoers.evildoerDie();
                }
                stringBuilder.append("击杀了"+creature+"!\n");
                result = stringBuilder.toString();
            }
            else {
                creature.setHealth(health-harm);
                //stringBuilder.append("对"+creature+"造成了:"+harm+"点伤害!\n");
                //result = stringBuilder.toString();
                result="";
            }
        }
    }

    @TODO(todo="返回子弹击中的文字描述")
    public String getResult() {
        return  result;
    }

    public Position getPos() {
        return pos;
    }
}
