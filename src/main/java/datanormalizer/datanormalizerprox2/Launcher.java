package datanormalizer.datanormalizerprox2;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
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

        //AtlantaFX Theme
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("panel-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), AppManager.WIDTH, AppManager.HEIGHT, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle(AppManager.TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();

        AppManager.printAppAttributes();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
