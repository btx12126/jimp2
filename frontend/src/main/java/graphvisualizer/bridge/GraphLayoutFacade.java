package graphvisualizer.bridge;

import graphvisualizer.model.GraphModel;

import java.io.File;
import java.io.IOException;

public class GraphLayoutFacade {
    private final ProcessRunner processRunner;
    private final BinaryParser binaryParser;

    public GraphLayoutFacade(ProcessRunner processRunner, BinaryParser binaryParser){
        this.processRunner = processRunner;
        this.binaryParser = binaryParser;
    }

    public void calculateLayout(File inputFile, LayoutStrategy strategy, GraphModel model) throws Exception {
        File tempOutputFile = File.createTempFile("layout_output", ".bin");
        tempOutputFile.deleteOnExit();

        try {
            int exitCode = processRunner.run(inputFile, tempOutputFile, strategy.getAlgorithmName());

            if (exitCode != 0) {
                String errorMsg = processRunner.getErrorMessage();
                if (errorMsg.isEmpty()){
                    errorMsg = "Backend C zakonczyl sie bledem o kodzie: " + exitCode;
                }
                throw new IOException(errorMsg);
            }

            binaryParser.parse(tempOutputFile, model);

            model.notifyListeners();
        }finally {
            if(tempOutputFile.exists()){
                tempOutputFile.delete();
            }
        }
    }
}
