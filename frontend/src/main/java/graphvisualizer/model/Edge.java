package graphvisualizer.model;

public class Edge {
    private final String name;
    private final int sourceId;
    private final int targetId;
    private final double weight;

    public Edge(String name, int sourceId, int targetId, double weight){
        this.name = name;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.weight = weight;
    }

    public String getName(){
        return name;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public double getWeight() {
        return weight;
    }
}
