package cvm.disassembler;

import java.io.*;
import java.util.*;

public class HexFileReader implements AutoCloseable {
    private final Scanner scanner;
    private boolean closed = false;

    public HexFileReader(String filePath) throws FileNotFoundException {
        scanner = new Scanner(new File(filePath));
        scanner.useDelimiter("\\s+");
    }

    /**
     * Проверяет наличие следующего байта
     * @return true если есть следующий непробельный байт
     * @throws IOException если ридер закрыт
     */
    public boolean hasNext() throws IOException {
        return !closed && scanner.hasNext();
    }

    /**
     * Читает указанное количество байтов
     * @param count запрашиваемое количество байтов
     * @return массив прочитанных байтов (может быть короче запрошенного)
     * @throws IOException при ошибках чтения или невалидных данных
     */
    public byte[] readBytes(int count) throws IOException {
        if (closed) throw new IOException("Reader closed");

        byte[] buffer = new byte[count];
        int bytesRead = 0;

        try {
            while (bytesRead < count && scanner.hasNext()) {
                String hex = scanner.next();
                buffer[bytesRead++] = (byte) Integer.parseInt(hex, 16);
            }
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IOException("Error parsing hex value", e);
        }

        return buffer;
    }

    @Override
    public void close() {
        if (!closed) {
            scanner.close();
            closed = true;
        }
    }
}