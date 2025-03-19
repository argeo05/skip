package utils;

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
     * Checks for the presence of the next byte
     *
     * @return true if there is a next non-space byte
     * @throws IOException if the reader is closed
     */

    public boolean hasNext() throws IOException {
        return !closed && scanner.hasNext();
    }

    /**
     * Reads the specified number of bytes
     *
     * @param count the requested number of bytes
     * @return array of read bytes
     * @throws IOException on read errors or invalid data
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