package njuczh;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import njuczh.Game.GameController;
import njuczh.Game.GameReview;
import org.junit.*;
public class TestGameReview {
    @Test(expected = Exception.class)
    public void loadLogTest() {
        GameReview gameReview = new GameReview(null, null,null);
    }
}
