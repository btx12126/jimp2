package graphvisualizer.controller;

import graphvisualizer.model.GraphModel;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileMenuController {
    private final GraphModel model;
    private final JFrame parentFrame;
    private File selectedFile;

    public FileMenuController(GraphModel model, JFrame parentFrame) {
        this.model = model;
        this.parentFrame = parentFrame;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void loadGraphFromFile(File file) throws IOException {
        model.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length < 4) {
                    throw new IOException("Blad w linie " + lineNumber + ": za malo kolumn.");
                }

                String edgeName = parts[0];
                int sourceId;
                int targetId;
                double weight;

                try {
                    sourceId = Integer.parseInt(parts[1]);
                    targetId = Integer.parseInt(parts[2]);
                    weight = Double.parseDouble(parts[3]);
                } catch (NumberFormatException e){
                    throw new IOException("Blad formatu liczb w lini " + lineNumber);
                }

                model.addEdge(edgeName, sourceId, targetId, weight);
            }
        }
    }

    public File selectAndLoadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wybierz plik z opisem krawedzi");

        int result = fileChooser.showOpenDialog(parentFrame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                loadGraphFromFile(file);
                this.selectedFile = file;
                return file;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parentFrame,
                        "Blad odczytu pliku: " + e.getMessage(),
                        "Blad",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
