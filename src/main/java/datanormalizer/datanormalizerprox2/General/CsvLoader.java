package datanormalizer.datanormalizerprox2.General;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CsvLoader {

    private static File lastLoadedFile; // Przechowuje ostatnio załadowany plik

    /**
     * Ładuje dane z pliku CSV do podanego TableView.
     *
     * @param file      Plik CSV do wczytania.
     * @param tableView Obiekt TableView, do którego dane zostaną załadowane.
     */
    public static void loadCsvData(File file, TableView<ObservableList<String>> tableView) {
        lastLoadedFile = file; // Zapisanie pliku jako ostatnio załadowanego
        reloadCsvData(tableView);
    }

    /**
     * Odświeża dane w TableView na podstawie ostatnio załadowanego pliku.
     *
     * @param tableView Obiekt TableView, którego dane mają być odświeżone.
     */
    public static void reloadCsvData(TableView<ObservableList<String>> tableView) {
        if (lastLoadedFile == null) {
            System.out.println("Brak pliku do odświeżenia!");
            return;
        }

        // Usunięcie istniejących kolumn i danych
        tableView.getColumns().clear();
        tableView.getItems().clear();

        try (BufferedReader br = new BufferedReader(new FileReader(lastLoadedFile))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (isHeader) {
                    // Tworzenie kolumn na podstawie nagłówków z pierwszego wiersza
                    for (int i = 0; i < values.length; i++) {
                        final int columnIndex = i;
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(values[i]);
                        column.setCellValueFactory(data ->
                                new SimpleStringProperty(data.getValue().get(columnIndex)));
                        tableView.getColumns().add(column);
                    }
                    isHeader = false;
                } else {
                    // Dodawanie danych jako wierszy
                    ObservableList<String> row = FXCollections.observableArrayList(Arrays.asList(values));
                    tableView.getItems().add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
