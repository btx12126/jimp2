package graphvisualizer.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static org.mockito.Mockito.verify;

class ViewportControllerTest {
    private CanvasInterface canvasMock;
    private ViewportController controller;
    private Component dummySource;

    @BeforeEach
    void setUp() {
        canvasMock = Mockito.mock(CanvasInterface.class);
        controller = new ViewportController(canvasMock);
        dummySource = new javax.swing.JPanel();
    }

    @Test
    void shouldMoveCameraOnMouseDrag() {
        MouseEvent pressEvent = new MouseEvent(dummySource, MouseEvent.MOUSE_PRESSED,
                System.currentTimeMillis(), 0, 100, 100, 1, false);
        controller.mousePressed(pressEvent);

        MouseEvent dragEvent = new MouseEvent(dummySource, MouseEvent.MOUSE_DRAGGED,
                System.currentTimeMillis(), 0, 150, 120, 1, false);
        controller.mouseDragged(dragEvent);

        verify(canvasMock).moveCamera(50, 20);
    }

    @Test
    void shouldZoomOnMouseWheel() {
        MouseWheelEvent wheelEvent = new MouseWheelEvent(dummySource, MouseEvent.MOUSE_WHEEL,
                System.currentTimeMillis(), 0, 0, 0, 0, false,
                MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, 1);

        controller.mouseWheelMoved(wheelEvent);

        verify(canvasMock).zoom(1);
    }
}