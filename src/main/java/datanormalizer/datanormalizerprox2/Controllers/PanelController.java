package datanormalizer.datanormalizerprox2.Controllers;
import datanormalizer.datanormalizerprox2.General.CsvLoader;
import datanormalizer.datanormalizerprox2.General.PythonScriptExecutor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PanelController {

    @FXML
    private Button loadButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button knnButton;

    @FXML
    private TableView<ObservableList<String>> tableViewRaw;

    // Obsługa przycisku "Załaduj plik CSV"
    @FXML
    public void handleLoadButtonAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Look for CSV file ...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        Stage stage = (Stage) loadButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            CsvLoader.loadCsvData(selectedFile, tableViewRaw);
        }
    }

    // Obsługa przycisku "Odśwież dane"
    @FXML
    public void handleRefreshButtonAction() {
        CsvLoader.reloadCsvData(tableViewRaw);
    }

    @FXML
    public void handleKnnButtonAction()
    {
        PythonScriptExecutor.knnExecutor(1);
    }
}
