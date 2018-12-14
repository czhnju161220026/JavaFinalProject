package njuczh.Game;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import njuczh.Battle.Battlefield;
import njuczh.Battle.Evildoers;
import njuczh.Battle.Heroes;
import njuczh.Formations.*;
import njuczh.MyAnnotation.Author;
import njuczh.Things.Creature;

/*负责处理GUI的逻辑 */
@Author(name = "崔子寒")
public class GameController implements Initializable{
    @FXML  Button startGame;
    @FXML  Button quitGame;
    @FXML  Button heroesChangeFormation;
    @FXML  Button evildoersChangeFormation;
    @FXML  Button loadLog;
    @FXML  TextArea gameLog;
    @FXML  Canvas gameArea;
    @FXML  AnchorPane aPane;
    private Image background = new Image("background.png");
    private Battlefield battlefield = new Battlefield();
    private Heroes heroes = new Heroes();
    private Evildoers evildoers = new Evildoers();
    private ArrayList<FormationProvider> providers = new ArrayList<FormationProvider>();
    private int currentFormationHero = 3;
    private int currentFormationEvil = 3;
    private boolean isGamming = false;
    private GameRound gameRound;
    private ExecutorService gameLauncher = Executors.newSingleThreadExecutor();


    //内部类：处理键盘事件
    //使用内部类是为了方便的使用GameController中的私有成员
    //游戏未开始时：上方向键葫芦娃变阵，下方向键妖怪变阵；
    //空格键游戏开始，开始后不能变阵；
    //游戏中ESC可以提前结束本轮
    class KeyBoredHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {
            if(!isGamming) {
                if(event.getCode()==KeyCode.SPACE) {
                    startGameHandler();
                }
                if(event.getCode()==KeyCode.UP) {
                    heroesChangeFormationHandler();
                }
                if(event.getCode()==KeyCode.DOWN) {
                    evildoersChangeFormationHandler();
                }
                if(event.getCode()==KeyCode.L) {
                    loadLogHandler();
                }
                if(event.getCode()==KeyCode.LEFT||event.getCode()==KeyCode.RIGHT) {
                    //do nothing
                }
            }
            else {
                if(event.getCode()==KeyCode.ESCAPE) {
                    quitGameHandler();
                }
            }
            //System.out.println(event.getCode());
        }
    }

    //内部类：处理用户在游戏途中关闭窗口导致有线程未释放的情况
    //调用quitGameHandler来清理线程
    //然后退出
    class ClickCloseHandler implements EventHandler<WindowEvent> {
        public void handle(WindowEvent event) {
            System.out.println("用户强制退出！");
            quitGameHandler();
            System.exit(0);
        }
    }

    //用户按下按钮或者通过键盘SPACE触发之后，自动将焦点设置到游戏区域
    @FXML private void startGameHandler() {
        if(!isGamming) {
            Stage stage = (Stage)aPane.getScene().getWindow();
            stage.setOnCloseRequest(new ClickCloseHandler());
            gameLog.clear();
            gameLog.appendText("游戏开始!\n");
            isGamming = true;
            Platform.runLater(new Runnable() {
                public void run() {
                    gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
                }
            });
            //游戏正式开始，开始随机移动和战斗
            gameLauncher = Executors.newSingleThreadExecutor();
            gameRound = new GameRound(heroes,evildoers,battlefield,gameArea.getGraphicsContext2D(),gameLog);
            gameLauncher.execute(gameRound);
            gameLauncher.shutdown();
        }
    }
    @FXML private void quitGameHandler() {
        if(isGamming) {
            GraphicsContext gc = gameArea.getGraphicsContext2D();
            gc.drawImage(background,0,0,1296,721);
            isGamming = false;
            try{
                gameRound.endGame();
                gameLauncher.shutdown();
                while(!gameLauncher.isTerminated()){}
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
            finally {
                battlefield = new Battlefield();
                heroes = new Heroes();
                evildoers = new Evildoers();
                currentFormationHero = 3;
                currentFormationEvil = 3;
                Creature.setBattlefield(battlefield.getBattlefield());
                heroes.changeFormation(providers.get(currentFormationHero),battlefield.getBattlefield());
                evildoers.changeFormation(providers.get(currentFormationEvil),battlefield.getBattlefield());
                battlefield.displayBattlefield(gameArea.getGraphicsContext2D());
            }
        }
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
            gc.drawImage(background,0,0,1296,721);
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
            gc.drawImage(background,0,0,1296,721);
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
    @FXML private void loadLogHandler() {
        System.out.println("加载存档");
        FileChooser logChooser = new FileChooser();
        logChooser.setTitle("选择游戏日志文件");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("选择myLog日志文件","*.myLog");
        logChooser.getExtensionFilters().add(extensionFilter);
        Stage stage = (Stage)aPane.getScene().getWindow();
        File log =logChooser.showOpenDialog(stage);
        System.out.println(log.getName());

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
        loadLog.setText("加载回放");
        heroesChangeFormation.setText("葫芦娃变阵");
        evildoersChangeFormation.setText("妖怪变阵");
        GraphicsContext gc = gameArea.getGraphicsContext2D();
        /*二维空间大小10单位 x 18单位,单位大小为70 x 70*/
        gc.drawImage(background,0,0,1296,721);
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
        gameArea.setOnKeyPressed(new KeyBoredHandler());
        providers.addAll(Arrays.asList(new HeYi(),new YanXing(),new ChongE(),
                new ChangShe(),new YuLin(),new Fang(),new YanYue(),new FengShi()));
        Creature.setBattlefield(battlefield.getBattlefield());
        heroes.changeFormation(providers.get(currentFormationHero),battlefield.getBattlefield());
        evildoers.changeFormation(providers.get(currentFormationEvil),battlefield.getBattlefield());
        battlefield.displayBattlefield(gameArea.getGraphicsContext2D());
    }
}
