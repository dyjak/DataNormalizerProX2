package datanormalizer.datanormalizerprox2.General;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PythonScriptExecutor {

    public static void executePythonScript(String scriptPath, String inputFilePath, String outputFilePath, int neighbors) {
        try {
            // Tworzenie komendy do uruchomienia Pythona
            List<String> command = new ArrayList<>();
            command.add("python"); // Lub "python3" w zależności od środowiska
            command.add(scriptPath);
            command.add(inputFilePath);
            command.add(outputFilePath);
            command.add(String.valueOf(neighbors));

            // Budowanie procesu
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Przekierowanie błędów do stdout

            // Uruchamianie procesu
            Process process = processBuilder.start();

            // Czytanie wyjścia skryptu Pythona
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Oczekiwanie na zakończenie procesu
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Skrypt zakończył się sukcesem.");
            } else {
                System.out.println("Skrypt zakończył się błędem. Kod wyjścia: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Przykładowe użycie
        String scriptPath = "path/to/script.py"; // Ścieżka do skryptu
        String inputFilePath = "path/to/input.csv"; // Plik wejściowy
        String outputFilePath = "path/to/output.csv"; // Plik wyjściowy
        int neighbors = 5; // Liczba sąsiadów

        executePythonScript(scriptPath, inputFilePath, outputFilePath, neighbors);
    }
}
