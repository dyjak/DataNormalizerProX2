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

    @FXML
    private TextField knnParamField;

    @FXML
    private TextField outlierParamField;

    @FXML
    private TextField clusterParamField;

    @FXML
    private TextField pcaParamField;

    @FXML
    private ComboBox<String> standardizeParamField;

    public void initialize() {
        initializeSettings();

        knnParamField.setText("3");
        outlierParamField.setText("0.1");
        clusterParamField.setText("4");
        pcaParamField.setText("5");
        standardizeParamField.setValue("minmax");
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
        String param = knnParamField.getText().isEmpty() ? "3" : knnParamField.getText();
        executeScript("knn", param);
    }

    @FXML
    public void handleClusterButtonAction() {
        String param = clusterParamField.getText().isEmpty() ? "4" : clusterParamField.getText();
        executeScript("kmeans", param);
    }

    @FXML
    public void handleOutlierDetectionButtonAction() {
        String param = outlierParamField.getText().isEmpty() ? "0.1" : outlierParamField.getText();
        executeScript("forest", param);
    }

    @FXML
    public void handleStandardizationButtonAction() {
        String param = standardizeParamField.getValue() == null ? "minmax" : standardizeParamField.getValue();
        executeScript("standardize", param);
    }

    @FXML
    public void handlePcaButtonAction() {
        String param = pcaParamField.getText().isEmpty() ? "5" : pcaParamField.getText();
        executeScript("pca", param);
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
