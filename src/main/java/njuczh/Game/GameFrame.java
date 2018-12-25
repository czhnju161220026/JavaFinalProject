package njuczh.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;



public class GameFrame {

    private List<GameElement> elements = new ArrayList<>();
    private List<String> text = new ArrayList<>();
    private static final Image background = new Image("background.png");
    private static final Image cure = new Image("cure.png");

    public void addElement(GameElement e) {
        elements.add(e);
    }
    public void addText(String str) {
        text.add(str);
    }
    public void displayFrame(GraphicsContext gc, TextArea textArea) {
        gc.drawImage(background,0,0,1296,721);
        for(GameElement e : elements) {
            gc.drawImage(e.getImage(),e.getX(),e.getY(),e.getWidth(),e.getHeight());
            if(e.isAlive()) {
                float ratio = e.getHealthRatio();
                gc.setFill(Color.RED);
                gc.fillRoundRect(e.getX()+3,e.getY(),66,5,10,10);
                gc.setFill(Color.color(0.3,1.0,0.69));
                gc.fillRoundRect(e.getX()+3,e.getY(),66*ratio,5,10,10);
            }
            if(e.isCured()) {
                gc.drawImage(cure,e.getX()+60,e.getY()-10,20,20);
            }
        }

        for(String str: text) {
            textArea.appendText(str+'\n');
        }
    }
}
