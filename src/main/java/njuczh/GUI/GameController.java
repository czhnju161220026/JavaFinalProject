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

public class GameController implements Initializable{
    @FXML  Button startGame;
    @FXML  Button quitGame;
    @FXML  Button heroesChangeFormation;
    @FXML  Button evildoersChangeFormation;
    @FXML  TextArea gameLog;
    @FXML  Canvas gameArea;

    /*
    *内部类：处理键盘事件
    * 使用内部类是为了方便的使用GameController中的成员
    * */
    class KeyBoredHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.SPACE) {
                startGameHandler();
            }
            System.out.println(event.getCode());
        }
    }
    //用户按下按钮之后，自动将焦点设置到游戏区域
    @FXML private void startGameHandler() {
        gameLog.appendText("游戏开始!\n");
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
    }
    @FXML private void quitGameHandler() {
        gameLog.appendText("游戏结束\n");
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
    }
    @FXML private void heroesChangeFormationHandler() {
        gameLog.appendText("葫芦娃阵营变阵\n");
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
    }
    @FXML private void evildoersChangeFormationHandler() {
        gameLog.appendText("怪物阵营变阵\n");
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
    }
    public void initialize(URL url, ResourceBundle rb) {
        gameLog.setText("游戏准备开始.\n");
        gameLog.setEditable(false);
        startGame.setText("开始游戏");
        quitGame.setText("结束游戏");
        heroesChangeFormation.setText("葫芦娃变阵");
        evildoersChangeFormation.setText("妖怪变阵");
        GraphicsContext gc = gameArea.getGraphicsContext2D();
        Image background = new Image("background.png");
        /*二维空间大小10单位 x 18单位,单位大小为70 x 70*/
        gc.drawImage(background,0,0,1260,711);
        Platform.runLater(new Runnable() {
            public void run() {
                gameArea.requestFocus();  //将用户行为的焦点设置到游戏区域
            }
        });
        gameArea.setOnKeyPressed(new KeyBoredHandler());
    }
}
