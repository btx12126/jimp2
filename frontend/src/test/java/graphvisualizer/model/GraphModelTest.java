package graphvisualizer.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphModelTest {
    private GraphModel model;

    @BeforeEach
    public void setUp() {
        model = new GraphModel();
    }

    @Test
    public void shouldAddNodesAndEdgesCorrectly() {
        String edgeName = "e1";
        int sourceId = 1;
        int targetId = 2;
        double weight = 1.5;

        model.addEdge(edgeName, sourceId, targetId, weight);

        assertEquals(2, model.getNode().size(), "Powinny byc dokladnie 2 wierzcholki");
        assertEquals(1, model.getEdges().size(), "Powinna byc dokladnie 1 krawedz");

        Edge edge = model.getEdges().get(0);
        assertEquals(edgeName, edge.getName());
        assertEquals(sourceId, edge.getSourceId());
        assertEquals(targetId, edge.getTargetId());
        assertEquals(weight, edge.getWeight());
    }

    @Test
    public void shouldNotifyObserverOnGraphChange() {
        final boolean[] notified = {false};
        GraphModelListener listener = () -> notified[0] = true;
        model.addListener(listener);

        model.addEdge("e1", 1, 2, 1.0);

        assertTrue(notified[0], "Obserwator powinien zostac powiadomiony o zmianie grafu");
    }
}
