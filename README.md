## 12月25日 更新
本次更新主要涉及到日志回放的功能的重写，之前的方法为：记录所有生物的路径，让其沿着之前的路径重新进行一次战斗。不过经过多次测试，有些情况下不能保证线程调度的顺序和原来游戏的调度顺序一样，从而导致有时子弹命中判定出现问题。进而影响了游戏结果。新的方法为：记录每一帧画面中的游戏元素，回放时读取文件，进行重绘复现。
<br>
添加新类如下:
**GameElement**: GameElement代表一幅画面上的一个游戏元素，可以是一个生物，一块墓碑，一发子弹，甚至是子弹命中的爆炸效果，它具有如下的域和方法：
``` java
private Image image;
private int x, y;
private int width;
private int height;
...
相关域访问器
```

**GameFrame**: GameFrame是一幅画面，它持有一个GameElement的容器，绘制一帧画面时，将容器中的所有GameElement绘制到指定位置即可，它具有的域与方法如下：
``` java
 private List<GameElement> elements = new ArrayList<>();
 //添加新元素，在读取日志时被调用
 public void addElement(GameElement e) {
    elements.add(e);
}
//绘制所有元素
public void displayFrame(GraphicsContext gc, TextArea textArea) {
    for(GameElement e : elements) {
          ...
          绘制e  
     }  
 }
```

另外修改了现有的类**GameReview**，GameReview解析日志，生成每一帧画面，创建出一个**GameFrame**的容器，在run方法中，按一定的时间间隔绘制这些画面。<br>
使用shade插件进行打包，执行mvn package后会生成两个.jar文件。其中JavaFinalProject-1.0-SNAPSHOT.jar已经设置了主类，可以直接双击运行。

## 12月16日 初次提交
### 葫芦娃大战妖精的故事
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
实现了Shoot接口的生物可以进行远程攻击，这里使用了工厂方法的设计思想，生物可以通过实现不同的shoot方法来生成不同的子弹。周期性的将子弹递交给控制游戏的线程，就实现了射击的功能。实现Cure接口可以为周围的友军提供治疗效果，通过设置友军的恢复状态，让友军恢复血量。
<br>
3. 交战事件
交战事件包括：(1)子弹命中生物。(2)生物在进行移动时，发现目的地已经有敌人占领，发生战斗。
首先来考虑单命中生物：单独定义一个类叫做**BulletHit**，它需要传进子弹和生物两个参数，然后处理子弹命中生物的事件。包括将子弹设置为已经命中，计算子弹对生物造成的伤害等。这样只要在子弹运行过程中进行检测，如果遇到了敌人，就生成一个BulletHit对象，交由游戏控制线程去处理，让代码变得很简洁。
``` java
package njuczh.Battle
public class BulletHit {
    private String result;
    private Position pos;
    public BulletHit(Bullet bullet, Creature creature) {...}
    @TODO(todo="返回子弹击中的文字描述")
    public String getResult() {}
}
```
生物遇到生物也是类似的定义了**CreaturesMeet**类，传入两个生物对象，自动的处理有关战斗的计算过程。
``` java
@TODO(todo="两个不同阵营的生物相遇的事件")
public class CreaturesMeet {
    private String result;
    public CreaturesMeet(Creature creature1,Creature creature2) {...}
    @TODO(todo="返回对战结果的文字描述")
    public String getResult() {...}
}
```
4. 并发同步
每一个生物都是Runnable的，每一个子弹也都是Runnable的。那么在战斗过程中需要对每一个线程进行控制，避免出现：(1)一个生物被杀死2次。(2)一个生物同时被近战、子弹杀死。(3)两个生物进入同一块空间。我所采用的并发同步方法是临界区控制：任何想要获得战场信息，或者在战场上进行移动的代码块都必须先获得在战场battlefied上的锁。当然也有其他的锁，比如攻击生物时要获得加在该生物上的锁，以避免多个生物攻击一个生物。
<br>
5. GUI的逻辑
GUI做的比较简单，有四个按钮(也支持键盘快捷键)：<br>
进入游戏界面后，可以通过**上下方向键**或者**对应的按钮**为葫芦娃和怪物变阵。选好阵型后按**空格**或者**开始游戏按钮**进入游戏。游戏结束后按**ESC**或者**结束**按钮准备开始下一轮游戏。另外在游戏准备开始的阶段按**L**或者加载按钮可以选择游戏日志进行加载。

#### 实现细节
1. Creature及其派生类
Creature是抽象基类，它的的派生类都实现了Runnable接口，它们各自具有不同的属性，并且提供了这些属性的访问器和变异器。另外还有一些同步时用到的静态变量。在public void run方法中，主要的代码逻辑如下：
``` java
public void run() {
    while(!isDead()) {
        Position next = nextMove();
        fight(next);
        //有技能的释放技能
        //在回血状态的回复血量
        ...
    }
}

public void fight(Position nextPosition) {
    synchronized (battlefield) {
            if(battlefield[i][j].isEmpty()) {
                moveTo(nextPos);
            }
            else {
                //是敌人就打一架
            }
    }
}
```
不同的生物根据具有的不同技能分别实现了不同的接口：
``` java
public class CalabashBrother extends Creature implements Shoot,Runnable{...}
public class Grandfather extends Creature implements Cure,Runnable{...}
public class Monster extends Creature implements Runnable, Shoot{...}
public class Scorpion extends Monster implements Runnable, Shoot{...}
public class Snake extends Monster implements Runnable, Cure{...}
```

2. 线程同步
如同在Creature的fight方法中那样，我采取的线程同步主要是设立临界区。如果要对battlefield进行敏感操作，必须先获得加在battlefield上的锁。
``` java
synchronized (battlefield) {
    //对battlefield进行修改操作，如移动，寻找对手。            
}
```
同样的，在其他需要同步的地方也需要同步，例如子弹的run方法中：
``` java
public void run() {
        int i = pos.getI();
        int j = pos.getJ();
        while(!isDone && pos.getX() > 0 && pos.getX() < 1296) {
            //子弹飞行 
            ...
            //如果进入了新的块
            //必须获得battlefield上的锁，才能进行子弹击中事件的判断
            synchronized (battlefield) {
                if(!battlefield[i][j].isEmpty()) {
                //该块非空，是敌人的话，进行命中事件的处理
                ...    
            }
            try{
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isDone = true;
        //System.out.println("Bullet done");
    }
```
还有在遍历打印战场的信息时也要进行同步。<br>
3. 战场空间
战场空间是18*10的二维空间，定义类Block为单位大小的战场空间。Block可以持有一个Creature对象。具体的接口如下：
``` java
package njuczh.Battle
public class Block{
    private Creature creature = null;
    private boolean isEmpty = true;
    public boolean creatureEnter(Creature creature) {...}
    public void creatureLeave(){...}
    public boolean isEmpty() {...}
    public Creature getCreature(){...}
    public Image getImage() {...}
}
```
战场Battlefield由一个若干个Block聚合而成。
``` java
public class Battlefield {
    private Block[][] battlefield= new Block[10][18];
    //构造函数
    public Battlefield() {...}
    //访问战场矩阵
    public Block[][] getBattlefield() {...}
    //绘制战场
    public void displayBattlefield(GraphicsContext gc) {...}
    //清理战场
    public void clearBattlefield() {...}
}
```
4. 阵型提供
 阵型提供者是一个实现了FormationProvider接口的类，它返回一个坐标数组，用来描述阵型。
``` java
/*给出一个坐标数组，描述指定的阵型*/
Position[] provideFormation();
/*返回阵型的名字*/
String getName();
```
一共实现了8种阵型，它们位于njuczh.Formations包中：
![image](https://github.com/czhnju161220026/image/blob/master/Formation.jpg?raw=true)
5. 双方阵营
双方的阵营由各自的一组生物构成,具有的方法如下：
![image](https://github.com/czhnju161220026/image/blob/master/group.png?raw=true)

6. 游戏过程
一局游戏本身也是一个线程，否则用户界面在游戏期间会没有响应。定义一个GameRound类的实现如下：
``` java
public class GameRound implements Runnable{
   ...
    public GameRound(Heroes heroes,Evildoers evildoers,Battlefield battlefield,GraphicsContext gc,TextArea textArea) {... }

    //初始化各个生物线程，并放入线程池中执行
    private void initThreads() {...}
    //游戏还未结束，不断展示游戏画面
    //游戏结束后，调用endGame进行清理
    private void displayAll() {
        while(isGamming) {...}
        endGame();
    }
    @TODO(todo = "输出每个生物的行进轨迹到日志")
    private void outputLog() {... }
    public void endGame() {...}

    @TODO(todo="进行线程的分配，游戏进行，游戏战斗的记录")
    public void run(){
        System.out.println("开始新回合");
        initThreads();
        displayAll();
        outputLog();
    }
}
```
这里将endGame设置为一个public的方法，是为了如果用户游戏途中直接退出，那么GUI框架会从外部调用endGame，以确保所有的线程都被正常的清理。

当用户按空格键或者按下了开始按钮后，会触发startGameHandler:
``` java
@FXML private void startGameHandler() {
        if(!isGamming&&!isReviewing) {
            ...
            isGamming = true;
            ...
            //游戏正式开始，开始随机移动和战斗
            gameLauncher = Executors.newSingleThreadExecutor();
            gameRound = new GameRound(heroes,evildoers,battlefield,gameArea.getGraphicsContext2D(),gameLog);
            gameLauncher.execute(gameRound);
            gameLauncher.shutdown();
        }
    }
```
gameLauncher是一个单线程线程池，它执行一个GameRound对象，并等待其结束。
7. 进行回放
回放的本质是将游戏的过程重现，这里我使用的方法是将每个生物的移动轨迹记下来。回放也是一局游戏，只不过是指定了生物运动路线的游戏。每个生物都有nextMove方法来获得下一步要前往的位置。回放和游戏的区别在于，回放是从记录好的路线中取位置，游戏是随机生成位置：
``` java
Position nextMove() {
        if(!isReviewing) {
            //生成随机行进路线
            ...
            ...
        }   
        //在回放状态，从trace中取出每一次的路线
        else {
            return tarce.get(index++);
        }
    }
```
#### 单元测试
对Creature类中生物移动，BulletHit事件，CreaturesMeet事件以及GUI的初始化进行了单元测试。
#### 面向对象的思想
1. 继承：CalabashBrother,GrandFahter,Monster等继承了基类Creature，而Creature又继承了Thing。更符合现实中的情形。
2. 组合：双方的阵营由一组生物组合聚类而成，二维空间由一组Block对象聚类而成。
3. 多态：Block持有一个Creature，但是它的所有方法都会被动态绑定到不同的子类，子类中对方法进行了不同的实现。
4. 工厂方法：一个生物如果有射击技能，就要实现Shoot接口，其中的shoot方法返回一个子弹对象，不需要知道该子弹的属性攻击力等如何设定，只要调用不同生物的shoot方法就可以获得所需要的子弹类型。
#### 编程体会
写代码是容易的，但是将代码写得可复用，易扩展是难的。不仅仅止步于完成作业，而要不断的迭代更新自己的代码，追求更好的代码是这学期的Java课程带给我的启示。


