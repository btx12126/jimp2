package graphvisualizer.view;

import graphvisualizer.model.GraphModel;
import graphvisualizer.controller.GraphController;
import graphvisualizer.controller.FileMenuController;
import graphvisualizer.bridge.GraphLayoutFacade;
import graphvisualizer.bridge.FRStrategy;
import graphvisualizer.bridge.TutteStrategy;
import graphvisualizer.bridge.ProcessRunner;
import graphvisualizer.bridge.BinaryParser;
import javax.swing.*;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    public MainFrame(){
        setTitle("Wizualizator Grafów Planarnych");
        setSize(1024,768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GraphModel graphModel=new GraphModel();
        GraphCanvas graphCanvas=new GraphCanvas(graphModel);

        ViewportController viewportController=new ViewportController(graphCanvas);
        graphCanvas.addMouseListener(viewportController);
        graphCanvas.addMouseMotionListener(viewportController);
        graphCanvas.addMouseWheelListener(viewportController);

        ControlPanel controlPanel=new ControlPanel();

        FileMenuController fileMenuController=new FileMenuController(graphModel,this);

        ProcessRunner processRunner=new ProcessRunner("backend/build/graph_layout");
        BinaryParser binaryParser=new BinaryParser();
        GraphLayoutFacade layoutFacade=new GraphLayoutFacade(processRunner,binaryParser);

        GraphController graphController=new GraphController(graphModel,layoutFacade,fileMenuController);
        graphController.setMainFrame(this);

        controlPanel.getLoadFileButton().addActionListener(e -> {
            fileMenuController.selectAndLoadFile();
            SwingUtilities.invokeLater(graphCanvas::centerGraph);
        });

        controlPanel.getStartButton().addActionListener(e->{
            if(controlPanel.getFrRadioButton().isSelected()){
                graphController.startLayoutCalculation(new FRStrategy());
            }else if(controlPanel.getTutteRadioButton().isSelected()){
                graphController.startLayoutCalculation(new TutteStrategy());
            }
        });

        add(controlPanel,BorderLayout.WEST);
        add(graphCanvas,BorderLayout.CENTER);
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainFrame window=new MainFrame();
        window.setVisible(true);
    }
}