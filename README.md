## 葫芦娃大战妖精的故事
![image](https://github.com/czhnju161220026/image/blob/master/display.gif?raw=true)
#### 游戏背景
葫芦七兄弟前往妖精洞穴解救爷爷，好不容易找到爷爷准备撤退时，正好撞见带着一队小怪巡逻的蛇精和蝎子精。于是双方就在洞穴中展开了激烈的战斗。
#### 游戏设定
1. 洞穴是一个大小18*10的二维空间，双方只能在这个空间内活动。
2. 在游戏开始之前，你可以为葫芦娃和妖怪排出你认为最强的阵型。游戏开始后，双方开始自由移动(速度为1单位/s)，相互攻击。攻击包括远程攻击和近战攻击。
3. 生物体死亡后，会在原地留下墓碑，墓碑会阻碍剩余的生物体的移动。墓碑3秒钟之后会自动消失(防止有可能生物被墓碑围住动弹不得)。
4. 某个阵营的生物全部阵亡后，游戏即结束。点击结束游戏按钮可以进入下一局游戏的准备阶段。
5. 葫芦兄弟、爷爷、妖精都身怀绝技，有着不同的属性和技能。具体的技能和属性描述，见下表：

<font size="1">
<table>
        <tr>
            <th>角色</th>
            <th>特性描述</th>
            <th>生命值</th>
            <th>攻击力</th>
            <th>远程攻击力</th>
            <th>防御力</th>
        </tr>
        <tr>
            <th>爷爷</th>
            <th>爷爷作为葫芦娃创造者，可以为周围受伤的葫芦娃治疗，但是爷爷的战斗能力较弱，需要葫芦娃的保护。</th>
            <th>500</th>
            <th>20</th>
            <th>0</th>
            <th>20</th>
        </tr>
        <tr>
            <th>大娃</th>
            <th>大娃力大无穷，攻高血厚，适合冲锋陷阵。</th>
            <th>500</th>
            <th>60</th>
            <th>50</th>
            <th>50</th>
        </tr>
        <tr>
            <th>二娃</th>
            <th>二娃属性均衡</th>
            <th>300</th>
            <th>40</th>
            <th>50</th>
            <th>40</th>
        </tr>
        <tr>
            <th>三娃</th>
            <th>三娃铜筋铁骨，防御高，普通的小怪无法攻破三娃的防御。</th>
            <th>300</th>
            <th>50</th>
            <th>50</th>
            <th>70</th>
        </tr>
        <tr>
            <th>四娃</th>
            <th>四娃掌握火球术，可以使他的远程攻击伤害增加。</th>
            <th>300</th>
            <th>50</th>
            <th>110</th>
            <th>50</th>
        </tr>
        <tr>
            <th>五娃</th>
            <th>五娃掌握水球术，远程伤害很高。</th>
            <th>300</th>
            <th>50</th>
            <th>110</th>
            <th>50</th>
        </tr>
        <tr>
            <th>六娃</th>
            <th>六娃会隐身术，可以闪避所有的远程攻击。</th>
            <th>300</th>
            <th>40</th>
            <th>50</th>
            <th>40</th>
        </tr>
        <tr>
            <th>七娃</th>
            <th>七娃有法宝葫芦，伤害高。</th>
            <th>300</th>
            <th>55</th>
            <th>50</th>
            <th>50</th>
        </tr>
        <tr>
            <th>蝎子精</th>
            <th>战斗力很高的大型妖怪，会喷出毒液进行攻击。</th>
            <th>600</th>
            <th>60</th>
            <th>60</th>
            <th>60</th>
        </tr>
        <tr>
            <th>蛇精</th>
            <th>攻击力很高，防御较低，可以为周围的妖怪提供治疗效果。</th>
            <th>500</th>
            <th>60</th>
            <th>0</th>
            <th>40</th>
        </tr>
        <tr>
            <th>普通小怪</th>
            <th>战斗力中等的小怪。</th>
            <th>270</th>
            <th>40</th>
            <th>50</th>
            <th>40</th>
        </tr>
    </table>

</font>
6. 伤害计算方法：远程攻击伤害 = 攻击力- 防御力(低于0以0计)；近战伤害 = 攻击力 - 防御力(低于0以10计).

#### 设计思路
1. 葫芦世界
![image](https://github.com/czhnju161220026/image/blob/master/class_final.png?raw=true)<br>
首先有一个最基础的类叫做**Thing**，Thing是葫芦世界中一个最基本的物。有了物就有了生物**Creature**，生物之间相互战斗，就需要武器，**Bullet**类就是它们战斗用的子弹。Creature派生出了不同的生物，也包括生物死亡后所遗留下来的**DeadCreature**。生物中有**Monster**，Monster又进化出了更高级的蛇精**Snake**和蝎子精**Scorpion**。
2. 身怀绝技
不同的人有着不同的技能，这是通过实现具体的技能接口来实现的。目前有两个接口：
``` java
/* njuczh.Skills.Shoot
*射击子弹的技能
*/
public interface Shoot {
    Bullet shoot();//产生子弹
}
/*njuczh.Skills.Cure
* 治疗友军
*/
public interface Cure {
    void cheer();//设置周围友军的恢复状态
}
```
实现了Shoot接口的生物可以进行远程攻击，这里使用了工厂方法的设计思想，生物可以通过实现不同的shoot方法来生成不同的子弹。周期性的将子弹递交给控制游戏的线程，就实现了射击的共呢个。实现Cure接口可以为周围的友军提供治疗效果，通过设置友军的恢复状态，让友军恢复血量。
3. 交战事件
交战事件包括：(1)子弹命中生物。(2)生物在进行移动时，发现目的地已经有敌人占领，发生战斗。
首先来考虑单命中生物：单独定义一个类叫做**BulletHit**，它需要传进子弹和生物两个参数，然后处理子弹命中生物的事件。包括将子弹设置为已经命中，计算子弹对生物造成的伤害等。这样只要在子弹运行过程中进行检测，如果遇到了敌人，就生成一个BulletHit对象，交由游戏控制线程去处理，让代码变得很简洁。生物遇到生物也是类似的定义了**CreaturesMeet**类，传入两个生物对象，自动的处理有关战斗的计算过程。
4. 并发同步
每一个生物都是Runnable的，每一个子弹也都是Runnable的。那么在战斗过程中需要对每一个线程进行控制，避免出现：(1)一个生物被杀死2次。(2)一个生物同时被近战、子弹杀死。(3)两个生物进入同一块空间。我所采用的并发同步方法是临界区控制：任何想要获得战场信息，或者在战场上进行移动的代码块都必须先获得在战场battlefied上的锁。当然也有其他的锁，比如攻击生物时要获得加在该生物上的锁，以避免多个生物攻击一个生物。
5. GUI的逻辑
GUI做的比较简单，有四个按钮(也支持键盘快捷键)：<br>
进入游戏界面后，可以通过上下方向键或者对应的按钮为葫芦娃和怪物变阵。选好阵型后按空格或者相应的按钮进入游戏。游戏结束后按ESC或者结束按钮准备开始下一轮游戏。另外在游戏准备开始的阶段按L或者加载按钮可以选择游戏日志进行加载。

#### 实现细节

#### 单元测试
#### 编程体会

