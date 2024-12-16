package datanormalizer.datanormalizerprox2.Controllers;
import datanormalizer.datanormalizerprox2.AppManager;
import datanormalizer.datanormalizerprox2.General.CsvLoader;
import datanormalizer.datanormalizerprox2.General.PythonScriptExecutor;
import datanormalizer.datanormalizerprox2.Launcher;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;

import java.io.File;
import java.io.IOException;

public class PanelController {

    //LOAD DATA
    @FXML
    private Button loadButton;

    @FXML
    private Button refreshButton;


    //SETTINGS
    @FXML
    private TextField filePathField;

    @FXML
    private TextField columnExploderField;

    @FXML
    private CheckBox fileHeadersCheckBox;
    @FXML
    private Button buttonSaveSettings;

    //SCRIPTS
    @FXML
    private Button knnButton;

    @FXML
    private TableView<ObservableList<String>> tableViewRaw;
    @FXML
    private TableView<ObservableList<String>> tableViewProcessed; // Usunięto "static"



    public void initialize() {
        initializeSettings();
    }

    @FXML
    public void initializeSettings() {
        // Wypełnianie pól wejściowych w ustawieniach z obecnych wartości
        filePathField.setText(AppManager.CURRENT_FILE != null ? AppManager.CURRENT_FILE.getAbsolutePath() : "none");
        columnExploderField.setText(AppManager.COLUMN_EXPLODER);
        fileHeadersCheckBox.setSelected(AppManager.COLUMN_HEADERS);
    }


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
        initializeSettings();
    }

    // Obsługa przycisku "Odśwież dane"
    @FXML
    public void handleRefreshButtonAction() {

        CsvLoader.reloadCsvData(tableViewRaw);
        initializeSettings();
    }

    @FXML
    public void saveSettings() {
        // Przypisanie wartości do AppManager
        AppManager.CURRENT_FILE = new File(filePathField.getText());
        AppManager.COLUMN_EXPLODER = columnExploderField.getText();
        AppManager.COLUMN_HEADERS = fileHeadersCheckBox.isSelected();

        // Potwierdzenie zapisanych wartości (możesz też zaktualizować GUI czy inne komponenty)
        System.out.println("Settings saved:");
        System.out.println("Current File Path: " + AppManager.CURRENT_FILE.getAbsolutePath());
        System.out.println("Column Exploder: " + AppManager.COLUMN_EXPLODER);
        System.out.println("File Has Headers: " + AppManager.COLUMN_HEADERS);

        handleRefreshButtonAction();
    }






    @FXML
    public void handleKnnButtonAction() throws IOException {
        PythonScriptExecutor.knnExecutor(1);

        File selectedFile = new File(AppManager.OUTPUT_PATH);
        if (selectedFile != null) {
            CsvLoader.loadCsvData(selectedFile, tableViewProcessed);
        }
        else System.out.println("WTF");
        initializeSettings();
    }
}
