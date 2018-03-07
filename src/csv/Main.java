package csv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("csv.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Controller controller = loader.getController();
        controller.setMain(this);

        primaryStage.setTitle("CSV Reader");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/image/honoka.jpg")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
