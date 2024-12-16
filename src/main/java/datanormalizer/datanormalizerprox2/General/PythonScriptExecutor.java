package datanormalizer.datanormalizerprox2.General;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import datanormalizer.datanormalizerprox2.AppManager;
import datanormalizer.datanormalizerprox2.Controllers.PanelController;

public class PythonScriptExecutor {

    public static void knnExecutor(int neighbors) throws IOException {
        String outputFilePath = null;
        try {
            String scriptPath = "E:\\SHARED\\Informatyka URZ\\S5\\IMED\\DataNormalizerProX2\\src\\main\\java\\datanormalizer\\datanormalizerprox2\\Scripts\\\\knn.py";
            String inputFilePath = AppManager.CURRENT_FILE.getAbsolutePath();
            System.out.println("outputFilePath: " + AppManager.OUTPUT_PATH);
            // Tworzenie komendy do uruchomienia Pythona
            List<String> command = new ArrayList<>();
            command.add("py");
            command.add("\"" + scriptPath + "\"");
            command.add("\"" + inputFilePath + "\"");
            command.add("\"" + AppManager.OUTPUT_PATH + "\"");
            command.add(String.valueOf(neighbors));

            System.out.println(command);

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
                System.out.println("Script success.");
            } else {
                System.out.println("Script error. Code: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
