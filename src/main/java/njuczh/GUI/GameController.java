package njuczh.GUI;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import njuczh.Battle.Battlefield;
import njuczh.Battle.Evildoers;
import njuczh.Battle.Heroes;
import njuczh.Formations.*;
import njuczh.Things.Bullet;


public class GameController implements Initializable{
    @FXML  Button startGame;
    @FXML  Button quitGame;
    @FXML  Button heroesChangeFormation;
    @FXML  Button evildoersChangeFormation;
    @FXML  TextArea gameLog;
    @FXML  Canvas gameArea;
    private Image background = new Image("background.png");
    private Heroes heroes = new Heroes();
    private Evildoers evildoers = new Evildoers();
    private Battlefield battlefield = new Battlefield();
    private ArrayList<FormationProvider> providers = new ArrayList<FormationProvider>();
    private int currentFormationHero = 3;
    private int currentFormationEvil = 3;
    boolean isGamming = false;

    /*
    *内部类：处理键盘事件
    * 使用内部类是为了方便的使用GameController中的私有成员
    * 收到空格按键后，游戏开始
    * */
    class KeyBoredHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.SPACE&&!isGamming) {
                startGameHandler();
            }
            System.out.println(event.getCode());
        }
    }
    //用户按下按钮或者通过键盘SPACE触发之后，自动将焦点设置到游戏区域
    @FXML private void startGameHandler() {
        gameLog.appendText("游戏开始!\n");
        isGamming = true;
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });

        //游戏正式开始，开始随机移动和战斗
    }
    @FXML private void quitGameHandler() {
        gameLog.appendText("游戏结束\n");
        isGamming = false;
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
    }
    @FXML private void heroesChangeFormationHandler() {
        if(!isGamming) {
            gameLog.appendText("葫芦娃阵营变阵:");
            currentFormationHero = (currentFormationHero+1)%providers.size();
            battlefield.clearBattlefield();
            GraphicsContext gc = gameArea.getGraphicsContext2D();
            gc.drawImage(background,0,0,1260,711);
            String formationName = heroes.changeFormation(providers.get(currentFormationHero),battlefield.getBattlefield());
            evildoers.changeFormation(providers.get(currentFormationEvil),battlefield.getBattlefield());
            gameLog.appendText(formationName+'\n');
            battlefield.displayBattlefield(gc);
        }
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
    }
    @FXML private void evildoersChangeFormationHandler() {
        if(!isGamming) {
            gameLog.appendText("怪物阵营变阵:");
            currentFormationEvil = (currentFormationEvil+1)%providers.size();
            battlefield.clearBattlefield();
            GraphicsContext gc = gameArea.getGraphicsContext2D();
            gc.drawImage(background,0,0,1260,711);
            String formationName = evildoers.changeFormation(providers.get(currentFormationEvil),battlefield.getBattlefield());
            heroes.changeFormation(providers.get(currentFormationHero),battlefield.getBattlefield());
            gameLog.appendText(formationName+'\n');
            battlefield.displayBattlefield(gc);
        }
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
    }

    public void initialize(URL url, ResourceBundle rb) {
        gameLog.setText("游戏准备开始.葫芦娃和妖怪摆好了长蛇阵！\n");
        gameLog.setEditable(false);
        startGame.setText("开始游戏");
        quitGame.setText("结束游戏");
        heroesChangeFormation.setText("葫芦娃变阵");
        evildoersChangeFormation.setText("妖怪变阵");
        GraphicsContext gc = gameArea.getGraphicsContext2D();
        /*二维空间大小10单位 x 18单位,单位大小为70 x 70*/
        gc.drawImage(background,0,0,1260,711);
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
        gameArea.setOnKeyPressed(new KeyBoredHandler());
        providers.addAll(Arrays.asList(new HeYi(),new YanXing(),new ChongE(),
                new ChangShe(),new YuLin(),new Fang(),new YanYue(),new FengShi()));
        heroes.changeFormation(providers.get(currentFormationHero),battlefield.getBattlefield());
        evildoers.changeFormation(providers.get(currentFormationEvil),battlefield.getBattlefield());
        battlefield.displayBattlefield(gameArea.getGraphicsContext2D());
    }
}
