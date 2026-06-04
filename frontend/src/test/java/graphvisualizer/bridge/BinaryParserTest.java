package graphvisualizer.bridge;

import graphvisualizer.model.GraphModel;
import graphvisualizer.model.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryParserTest {
    private BinaryParser parser;
    private GraphModel model;

    @BeforeEach
    public void setUp() {
        parser = new BinaryParser();
        model = new GraphModel();
    }

    @Test
    public void shouldParserValidBinaryFileCorrectly() throws Exception {
        model.addEdge("e1", 1, 2, 1.0);

        File tempFile = File.createTempFile("test_valid", ".bin");
        tempFile.deleteOnExit();

        try (java.io.OutputStream os = new java.io.FileOutputStream(tempFile)) {
            os.write(new byte[]{1, 0, 0, 0});

            long xBits = Double.doubleToRawLongBits(100.5);
            os.write(toBytesLittleEndian(xBits));

            long yBits = Double.doubleToRawLongBits(200.25);
            os.write(toBytesLittleEndian(yBits));
        }
        parser.parse(tempFile, model);

        Node node = model.getNode().get(1);
        assertNotNull(node);
        assertEquals(100.5, node.getX(), 0.0001);
        assertEquals(200.25, node.getY(), 0.0001);

        tempFile.delete();
    }

    private byte[] toBytesLittleEndian(long value) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) ((value >> (i * 8)) & 0xFF);
        }
        return bytes;
    }

    @Test
    public void shouldThrowExceptionWhenFileNotFound() throws Exception {
        File nonExistentFile = new File("nieistniejacy_plik.bin");
        assertThrows(FileNotFoundException.class, () -> {
            parser.parse(nonExistentFile, model);
        });
    }

    @Test
    public void shouldThrowExceptionWhenFileIsEmpty() throws Exception {
        File tempFile = File.createTempFile("pusty_test", ".bin");
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {

        }

        assertThrows(IOException.class, () -> {
            parser.parse(tempFile, model);
        });

        tempFile.delete();
    }

    @Test
    public void shouldThrowExceptionWhenFileNotMultipleOf20Bytes() throws Exception {
        File tempFile = File.createTempFile("niepoprawny_rozmiar", ".bin");
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(new byte[15]);
        }

        assertThrows(IOException.class, () -> {
            parser.parse(tempFile,model);
        });

        tempFile.delete();
    }
}
