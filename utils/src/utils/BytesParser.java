package utils;

public final class BytesParser {
    public static long toDeciminal(byte[] bytes) {
        long result = 0;

        for (int i = 0; i < bytes.length; i++) {
            result |= (long) (bytes[i] & 0xFF) << (8 * (bytes.length - 1 - i));
        }

        return result;
    }

    public static byte[] toBytes(long value, int byteCount) {
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            bytes[i] = (byte) ((value >> (8 * (byteCount - 1 - i))) & 0xFF);
        }
        return bytes;
    }
}
