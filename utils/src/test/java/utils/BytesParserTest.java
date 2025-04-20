package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BytesParserTest {

    @Test
    void testToDeciminalWithEmptyArray() {
        assertEquals(0L, BytesParser.toDeciminal(new byte[0]));
    }

    @Test
    void testToDeciminalWithSingleByte() {
        byte[] bytes = {(byte) 0xFF};
        assertEquals(255L, BytesParser.toDeciminal(bytes));
    }

    @Test
    void testToDeciminalWithMultipleBytes() {
        byte[] bytes = {0x01, 0x02, 0x03, 0x04};
        assertEquals(0x01020304L, BytesParser.toDeciminal(bytes));
    }

    @Test
    void testToBytesZeroValue() {
        byte[] bytes = BytesParser.toBytes(0L, 4);
        assertArrayEquals(new byte[]{0, 0, 0, 0}, bytes);
    }

    @Test
    void testToBytesKnownValue() {
        byte[] bytes = BytesParser.toBytes(0x01020304L, 4);
        assertArrayEquals(new byte[]{1, 2, 3, 4}, bytes);
    }

    @Test
    void testRoundTrip() {
        long value = 0xDEADBEEFL;
        int length = 4;
        byte[] bytes = BytesParser.toBytes(value, length);
        assertEquals(value, BytesParser.toDeciminal(bytes));
    }
}