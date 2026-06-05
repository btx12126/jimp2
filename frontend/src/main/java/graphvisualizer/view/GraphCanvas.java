package graphvisualizer.view;

import graphvisualizer.model.Edge;
import graphvisualizer.model.GraphModel;
import graphvisualizer.model.Node;
import javax.swing.JPanel;
import java.awt.*;

public class GraphCanvas extends JPanel implements CanvasInterface {
    private double offsetX=0, offsetY=0;
    private double scale=1.0;
    private final GraphModel graphModel;

    public GraphCanvas(GraphModel graphModel){
        this.graphModel=graphModel;
        this.graphModel.addListener(this::centerGraph);
        setBackground(new Color(30,30,30));
    }

    public void moveCamera(int dx,int dy){
        this.offsetX+=dx;
        this.offsetY+=dy;
        repaint();
    }

    public void zoom(int clicks){
        if (clicks > 0) {
            scale *= 0.9;
        } else {
            scale *= 1.1;
        }
        repaint();
    }

    public void centerGraph() {
        if (graphModel.getNode().isEmpty()) return;

        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;

        for (Node n : graphModel.getNode().values()) {
            if (n.getX() < minX) minX = n.getX();
            if (n.getX() > maxX) maxX = n.getX();
            if (n.getY() < minY) minY = n.getY();
            if (n.getY() > maxY) maxY = n.getY();
        }

        double graphWidth = maxX - minX;
        double graphHeight = maxY - minY;

        if (graphWidth == 0) graphWidth = 1;
        if (graphHeight == 0) graphHeight = 1;

        double scaleX = (getWidth() - 100) / graphWidth;
        double scaleY = (getHeight() - 100) / graphHeight;

        scale = Math.min(scaleX, scaleY);
        if (scale < 0.01) scale = 0.01;

        double avgX = (minX + maxX) / 2.0;
        double avgY = (minY + maxY) / 2.0;

        offsetX = (getWidth() / 2.0) - (avgX * scale);
        offsetY = (getHeight() / 2.0) - (avgY * scale);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if(graphModel==null) return;

        g2d.setColor(new Color(100,100,100,80));
        for(Edge edge:graphModel.getEdges()){
            Node source=graphModel.getNode().get(edge.getSourceId());
            Node target=graphModel.getNode().get(edge.getTargetId());
            if(source!=null&&target!=null){
                g2d.drawLine(
                        (int)(source.getX() * scale + offsetX),
                        (int)(source.getY() * scale + offsetY),
                        (int)(target.getX() * scale + offsetX),
                        (int)(target.getY() * scale + offsetY)
                );
            }
        }

        g2d.setColor(new Color(0,200,255));
        int radius=3;
        for(Node n:graphModel.getNode().values()){
            g2d.fillOval(
                    (int)(n.getX() * scale + offsetX) - radius,
                    (int)(n.getY() * scale + offsetY) - radius,
                    radius*2, radius*2
            );
        }
    }
}