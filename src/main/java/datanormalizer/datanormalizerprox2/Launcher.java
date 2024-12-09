package datanormalizer.datanormalizerprox2;
import datanormalizer.datanormalizerprox2.Controllers.PanelController;
import datanormalizer.datanormalizerprox2.General.CsvLoader;
import datanormalizer.datanormalizerprox2.AppManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("panel-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), AppManager.WIDTH, AppManager.HEIGHT, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle(AppManager.TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
