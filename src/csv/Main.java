package csv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("csv.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Controller controller = loader.getController();
        controller.setMain(this);

        primaryStage.setTitle("CSV Reader");
        InputStream resource = getClass().getResourceAsStream("/image/honoka.jpg");
        if (resource == null) {
            logger.severe("init error");
            return;
        }
        primaryStage.getIcons().add(new Image(resource));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
