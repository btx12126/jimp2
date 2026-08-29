package graphvisualizer.view;

public interface CanvasInterface {
    void moveCamera(int dx, int dy);
    void zoom(int factor);
    void centerGraph();
}