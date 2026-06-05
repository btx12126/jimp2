package graphvisualizer.view;

import graphvisualizer.model.GraphModel;
import graphvisualizer.model.GraphModelListener;
import graphvisualizer.model.Node;
import java.util.HashMap;
import java.util.Map;

public class GraphModelStub extends GraphModel {
    @Override
    public void addListener(GraphModelListener listener) {
    }

    @Override
    public Map<Integer, Node> getNode() {
        return new HashMap<>();
    }
}