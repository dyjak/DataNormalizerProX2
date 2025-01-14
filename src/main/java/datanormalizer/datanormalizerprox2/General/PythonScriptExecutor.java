package datanormalizer.datanormalizerprox2.General;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PythonScriptExecutor {

    public static void executeScript(String scriptName, List<String> args) {
        try {
            String scriptPath = getScriptPath(scriptName);
            validateFilePath(scriptPath);

            List<String> command = buildCommand(scriptPath, args);
            System.out.println("Executing command: " + String.join(" ", command));

            Process process = new ProcessBuilder(command).redirectErrorStream(true).start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Script execution failed with exit code: " + exitCode);
            }

            System.out.println("Script executed successfully.");

        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Execution error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getScriptPath(String scriptName) {
        return new File("src/main/java/datanormalizer/datanormalizerprox2/Scripts/scripts_all.py").getAbsolutePath();
    }

    private static void validateFilePath(String path) throws IOException {
        if (!new File(path).exists()) {
            throw new IOException("Script not found: " + path);
        }
    }

    private static List<String> buildCommand(String scriptPath, List<String> args) {
        List<String> command = new ArrayList<>();
        command.add("py");
        command.add(scriptPath);
        command.addAll(args);
        return command;
    }

    public static void execute(String mode, String inputPath, String outputPath, String param) {
        List<String> args = new ArrayList<>();
        args.add(mode); // Tryb pracy skryptu (np. "knn", "kmeans")
        args.add(inputPath); // Ścieżka pliku wejściowego
        args.add(outputPath); // Ścieżka pliku wyjściowego
        args.add(param); // Parametr specyficzny dla trybu
        executeScript(getScriptPath("scripts_all"), args);
    }
}
