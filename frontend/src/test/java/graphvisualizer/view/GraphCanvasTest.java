package graphvisualizer.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GraphCanvasTest {
    private GraphCanvas canvas;

    @BeforeEach
    void setUp() {
        GraphModelStub modelStub = new GraphModelStub();
        canvas = new GraphCanvas(modelStub);
    }

    @Test
    void shouldRegisterListenerOnCreation() {
    }

    @Test
    void shouldNotThrowExceptionWhenCenteringEmptyGraph() {
        assertDoesNotThrow(() -> canvas.centerGraph());
    }
}