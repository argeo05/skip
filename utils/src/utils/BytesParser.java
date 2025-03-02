package utils;

public final class BytesParser {
    public static long toDeciminal(byte[] bytes) {
        long result = 0;

        for (int i = 0; i < bytes.length; i++) {
            result |= (long) (bytes[i] & 0xFF) << (8 * (bytes.length - 1 - i));
        }

        return result;
    }
}
