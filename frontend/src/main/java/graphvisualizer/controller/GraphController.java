package graphvisualizer.controller;

import graphvisualizer.bridge.GraphLayoutFacade;
import graphvisualizer.bridge.LayoutStrategy;
import graphvisualizer.model.GraphModel;
import graphvisualizer.view.MainFrame;
import javax.swing.SwingWorker;
import javax.swing.JOptionPane;
import java.io.File;

public class GraphController {
    private final GraphModel model;
    private final GraphLayoutFacade layoutFacade;
    private final FileMenuController fileMenuController;
    private MainFrame mainFrame;

    public GraphController(GraphModel model, GraphLayoutFacade layoutFacade, FileMenuController fileMenuController) {
        this.model = model;
        this.layoutFacade = layoutFacade;
        this.fileMenuController = fileMenuController;
    }

    public void setMainFrame(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    public void startLayoutCalculation(LayoutStrategy strategy) {
        File inputFile = fileMenuController.getSelectedFile();
        if (inputFile == null) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Najpierw wczytaj plik z grafem!",
                    "Ostrzeżenie",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                layoutFacade.calculateLayout(inputFile, strategy, model);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(mainFrame,
                            "Obliczenia zakonczone sukcesem!",
                            "Sukces",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    JOptionPane.showMessageDialog(mainFrame,
                            "Blad podczas oblicznia rozkladu grafu:\n" + cause.getMessage(),
                    "Blad",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }
}
