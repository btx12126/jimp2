package graphvisualizer.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ViewportController extends MouseAdapter {
    private final CanvasInterface canvas;
    private int lastX;
    private int lastY;

    public ViewportController(CanvasInterface canvas){
        this.canvas=canvas;
    }

    @Override
    public void mousePressed(MouseEvent e){
        lastX=e.getX();
        lastY=e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e){
        int dx=e.getX()-lastX;
        int dy=e.getY()-lastY;
        canvas.moveCamera(dx,dy);
        lastX=e.getX();
        lastY=e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        canvas.zoom(e.getWheelRotation());
    }
}