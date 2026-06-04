package graphvisualizer.model;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class GraphModel {
    private final Map<Integer, Node> nodes = new LinkedHashMap<>();
    private final List<Edge> edges = new ArrayList<>();
    private final List<GraphModelListener> listeners = new ArrayList<>();

    public void addListener(GraphModelListener listener){
        listeners.add(listener);
    }

    public void removeListener(GraphModelListener listener){
        listeners.remove(listener);
    }

    public void notifyListeners(){
        for (GraphModelListener listener : listeners){
            listener.onGraphChanged();
        }
    }

    public void addEdge(String name, int sourceId, int targetId, double weight){
        if(!nodes.containsKey(sourceId)){
            nodes.put(sourceId, new Node(sourceId));
        }
        if(!nodes.containsKey(targetId)){
            nodes.put(targetId, new Node(targetId));
        }
        edges.add(new Edge(name, sourceId, targetId, weight));
        notifyListeners();
    }

    public void updateNodePosition(int id, double x, double y){
        Node node = nodes.get(id);
        if (node != null){
            node.setX(x);
            node.setY(y);
        }
    }

    public void clear(){
        nodes.clear();
        edges.clear();
        notifyListeners();
    }

    public List<Edge> getEdges(){
        return new ArrayList<>(edges);
    }

    public Map<Integer, Node> getNode(){
        return new LinkedHashMap<>(nodes);
    }
}
