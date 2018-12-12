package njuczh.Battle;

import njuczh.MyAnnotation.TODO;
import njuczh.Things.Bullet;
import njuczh.Things.Creature;

@TODO(todo="子弹击中某个生物的事件")
public class BulletHit {
    private String result;
    public BulletHit(Bullet bullet, Creature creature) {
        StringBuilder stringBuilder = new StringBuilder("");
        int attackPower = bullet.getAttackPower();
        String shooterName = bullet.getShooterName();
        bullet.setDone();
        int harm = attackPower-creature.getDenfensePower();
        if(harm<=0) {
            stringBuilder.append(shooterName);
            stringBuilder.append("的远程攻击未能击穿"+creature+"的防御!\n");
            result = stringBuilder.toString();
        }
        else {
            stringBuilder.append(shooterName);
            if(harm >= creature.getHealth()) {
                creature.setHealth(0);
                stringBuilder.append("的远程攻击击杀了"+creature+"!\n");
                result = stringBuilder.toString();
            }
            else {
                creature.setHealth(creature.getHealth()-harm);
                stringBuilder.append("对"+creature+"造成了:"+harm+"点伤害!\n");
                result = stringBuilder.toString();
            }
        }
    }

    @TODO(todo="返回子弹击中的文字描述")
    public String getResult() {
        return  result;
    }
}
