package graphvisualizer.bridge;

import graphvisualizer.model.GraphModel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BinaryParser {
    public void parse(File file, GraphModel model) throws IOException{
        if (!file.exists()) {
            throw new FileNotFoundException("Nie znaleziono pliku binarnego: " + file.getPath());
        }

        long length = file.length();
        if (length == 0) {
            throw new IOException("Plik binarny jest pusty");
        }
        if (length % 20 != 0) {
            throw new IOException("Nieprawidlowy rozmiar pliku binarnego. Rozmiar musi być wielokrotnoscia 20 bajtow");
        }

        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[20];
            while (is.read(buffer) == 20) {
                ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);

                int id = bb.getInt();
                double x = bb.getDouble();
                double y = bb.getDouble();

                model.updateNodePosition(id, x, y);
            }
        }
    }
}
