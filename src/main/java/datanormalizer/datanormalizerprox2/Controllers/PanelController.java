package datanormalizer.datanormalizerprox2.Controllers;

import datanormalizer.datanormalizerprox2.General.CsvLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PanelController {

    @FXML
    private Button loadButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<ObservableList<String>> tableView;

    // Obsługa przycisku "Załaduj plik CSV"
    @FXML
    public void handleLoadButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki CSV", "*.csv"));

        Stage stage = (Stage) loadButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            CsvLoader.loadCsvData(selectedFile, tableView);
        }
    }

    // Obsługa przycisku "Odśwież dane"
    @FXML
    public void handleRefreshButtonAction() {
        CsvLoader.reloadCsvData(tableView);
    }
}
