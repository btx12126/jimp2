package graphvisualizer.bridge;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProcessRunner {
    private final String executablePath;
    private String errorMessage = "";

    public ProcessRunner(String executablePath){
        this.executablePath = executablePath;
    }

    public int run(File inputFile, File outputFile, String algorithm) throws Exception{
        List<String> command = List.of(
                executablePath,
                "-i", inputFile.getAbsolutePath(),
                "-o", outputFile.getAbsolutePath(),
                "-a", algorithm,
                "-f", "bin"
        );
        ProcessBuilder pb = new ProcessBuilder(command);

        Process process = pb.start();

        StringBuilder errorBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                errorBuilder.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();

        this.errorMessage = errorBuilder.toString().trim();

        return exitCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
