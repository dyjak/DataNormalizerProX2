package datanormalizer.datanormalizerprox2.General;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import datanormalizer.datanormalizerprox2.AppManager;

public class PythonScriptExecutor {

    public static void knnExecutor(int neighbors) {
        try {
            String scriptPath = "D:\\SHARED\\Informatyka URZ\\S5\\IMED\\DataNormalizerProX2\\src\\main\\java\\datanormalizer\\datanormalizerprox2\\Scripts\\.venv\\knn.py";
            String inputFilePath = AppManager.CURRENT_FILE.getAbsolutePath();
            String outputFilePath = AppManager.CURRENT_FILE.getPath().concat("test-processed.csv");
            System.out.println("outputFilePath: " + outputFilePath);
            // Tworzenie komendy do uruchomienia Pythona
            List<String> command = new ArrayList<>();
            command.add("py");
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
}
