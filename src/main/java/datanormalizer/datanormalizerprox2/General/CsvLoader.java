package datanormalizer.datanormalizerprox2.General;

import datanormalizer.datanormalizerprox2.AppManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvLoader {

    private static File lastLoadedFile; // Przechowuje ostatnio załadowany plik

    /**
     * Ładuje dane z pliku CSV do podanego TableView.
     *
     * @param file      Plik CSV do wczytania.
     * @param tableView Obiekt TableView, do którego dane zostaną załadowane.
     */
    public static void loadCsvData(File file, TableView<ObservableList<String>> tableView) throws IOException {
        lastLoadedFile = file; // Zapisanie pliku jako ostatnio załadowanego
        AppManager.CURRENT_FILE=file;
        AppManager.updateOutputPath();
        reloadCsvData(tableView);
    }

    /**
     * Odświeża dane w TableView na podstawie ostatnio załadowanego pliku.
     *
     * @param tableView Obiekt TableView, którego dane mają być odświeżone.
     */

    public static void reloadCsvData(TableView<ObservableList<String>> tableView) {
        if (lastLoadedFile == null) {
            System.out.println("No file selected!");
            return;
        }

        // Usunięcie istniejących kolumn i danych
        tableView.getColumns().clear();
        tableView.getItems().clear();

        try (BufferedReader br = new BufferedReader(new FileReader(lastLoadedFile))) {
            String line;
            boolean isHeader = true;
            int columnCount = 0; // Zmienna do przechowywania liczby kolumn

            while ((line = br.readLine()) != null) {
                String[] values = line.split(AppManager.COLUMN_EXPLODER);

                if (isHeader) {
                    // Tworzenie kolumn na podstawie nagłówków z pierwszego wiersza
                    columnCount = values.length; // Zapamiętanie liczby kolumn
                    for (int i = 0; i < columnCount; i++) {
                        final int columnIndex = i;
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(values[i]);
                        column.setCellValueFactory(data ->
                                new SimpleStringProperty(data.getValue().get(columnIndex)));
                        column.setPrefWidth((int) (AppManager.WIDTH / (columnCount + 1)));

                        // Ustawienie niestandardowego renderowania komórek
                        column.setCellFactory(col -> new TableCell<>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                    setStyle("");
                                } else {
                                    setText(item);
                                    // Wyróżnienie pustych wartości
                                    if (item.isEmpty()) {
                                        setStyle("-fx-background-color: lightcoral; -fx-text-fill: black;");
                                    } else {
                                        setStyle(""); // Domyślny styl
                                    }
                                }
                            }
                        });

                        tableView.getColumns().add(column);
                    }
                    isHeader = false;
                } else {
                    // Dodawanie danych jako wierszy
                    List<String> rowValues = new ArrayList<>(Arrays.asList(values));

                    // Uzupełnianie brakujących kolumn pustymi wartościami
                    while (rowValues.size() < columnCount) {
                        rowValues.add(""); // Dodanie pustego rekordu
                    }

                    ObservableList<String> row = FXCollections.observableArrayList(rowValues);
                    tableView.getItems().add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppManager.printAppAttributes();
    }

}
