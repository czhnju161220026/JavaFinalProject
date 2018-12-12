package njuczh.GUI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI.fxml"));
        primaryStage.setTitle("葫芦娃的世界");
        Scene scene = new Scene(root, 1600, 770);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("GUI.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}