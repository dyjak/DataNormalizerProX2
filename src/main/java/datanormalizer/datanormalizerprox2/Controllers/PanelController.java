package datanormalizer.datanormalizerprox2.Controllers;

import datanormalizer.datanormalizerprox2.AppManager;
import datanormalizer.datanormalizerprox2.General.CsvLoader;
import datanormalizer.datanormalizerprox2.General.PythonScriptExecutor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TextField filePathField;

    @FXML
    private TextField columnExploderField;

    @FXML
    private CheckBox fileHeadersCheckBox;

    @FXML
    private Button buttonSaveSettings;

    @FXML
    private Button knnButton;

    @FXML
    private Button clusterButton;

    @FXML
    private Button outlierButton;

    @FXML
    private Button standardizeButton;

    @FXML
    private Button pcaButton;

    @FXML
    private TableView<ObservableList<String>> tableViewRaw;

    @FXML
    private TableView<ObservableList<String>> tableViewProcessed;

    public void initialize() {
        initializeSettings();
    }

    @FXML
    public void initializeSettings() {
        filePathField.setText(AppManager.CURRENT_FILE != null ? AppManager.CURRENT_FILE.getAbsolutePath() : "none");
        columnExploderField.setText(AppManager.COLUMN_EXPLODER);
        fileHeadersCheckBox.setSelected(AppManager.COLUMN_HEADERS);
    }

    @FXML
    public void handleLoadButtonAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        Stage stage = (Stage) loadButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            CsvLoader.loadCsvData(selectedFile, tableViewRaw);
        }
        initializeSettings();
    }

    @FXML
    public void handleRefreshButtonAction() {
        CsvLoader.reloadCsvData(tableViewRaw);
        initializeSettings();
    }

    @FXML
    public void saveSettings() {
        AppManager.CURRENT_FILE = new File(filePathField.getText());
        AppManager.COLUMN_EXPLODER = columnExploderField.getText();
        AppManager.COLUMN_HEADERS = fileHeadersCheckBox.isSelected();
        handleRefreshButtonAction();
    }

    @FXML
    public void handleKnnButtonAction() {
        executeScript("knn", "3");
    }

    @FXML
    public void handleClusterButtonAction() {
        executeScript("kmeans", "4");
    }

    @FXML
    public void handleOutlierDetectionButtonAction() {
        executeScript("forest", "0.1");
    }

    @FXML
    public void handleStandardizationButtonAction() {
        executeScript("standardize", "minmax");
    }

    @FXML
    public void handlePcaButtonAction() {
        executeScript("pca", "2");
    }

    private void executeScript(String mode, String param) {
        try {
            PythonScriptExecutor.execute(mode, AppManager.CURRENT_FILE.getAbsolutePath(), AppManager.OUTPUT_PATH, param);

            File outputFile = new File(AppManager.OUTPUT_PATH);
            if (outputFile.exists()) {
                CsvLoader.loadCsvData(outputFile, tableViewProcessed);
            } else {
                throw new IOException("Output file not found.");
            }

        } catch (IOException e) {
            showAlert("Error", "File Processing Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Critical Error", "Unexpected Error Occurred", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
