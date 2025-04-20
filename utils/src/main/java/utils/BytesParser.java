package utils;

/**
 * Utility for converting between long values and byte arrays.
 */
public final class BytesParser {

    private BytesParser() {}

    /**
     * Interprets a big‑endian byte array as an unsigned integer and returns its value.
     *
     * @param bytes the big‑endian byte array
     * @return the decoded long value
     */
    public static long toDeciminal(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result |= (long)(bytes[i] & 0xFF) << (8 * (bytes.length - 1 - i));
        }
        return result;
    }

    /**
     * Encodes the given long value into a big‑endian byte array of the specified length.
     *
     * @param value     the value to encode
     * @param byteCount the length of the resulting byte array
     * @return a big‑endian byte array representing the value
     */
    public static byte[] toBytes(long value, int byteCount) {
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            bytes[i] = (byte)((value >> (8 * (byteCount - 1 - i))) & 0xFF);
        }
        return bytes;
    }
}