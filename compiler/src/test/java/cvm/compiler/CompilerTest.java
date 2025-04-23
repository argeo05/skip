package cvm.compiler;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class CompilerTest {
    @Test
    void testCompileOutput1() throws Exception {
        Path resDir = Paths.get("src", "test", "resources");
        Path input = resDir.resolve("inputcomplier1.txt");
        Path expected = resDir.resolve("outputcomplier1.sosa");
        Path output = resDir.resolve("result1.sosa");

        Files.deleteIfExists(output);

        new Compiler().compile(
                input.toString(),
                output.toString()
        );

        byte[] actualBytes = Files.readAllBytes(output);
        byte[] expectedBytes = Files.readAllBytes(expected);

        assertArrayEquals(
                expectedBytes,
                actualBytes);
    }

    @Test
    void testCompileOutput2() throws Exception {
        Path resDir = Paths.get("src", "test", "resources");
        Path input = resDir.resolve("inputcomplier2.txt");
//        Path expected = resDir.resolve("outputcomplier2.sosa");
        Path output = resDir.resolve("result2.sosa");

        Files.deleteIfExists(output);

        new Compiler().compile(
                input.toString(),
                output.toString()
        );

        byte[] actualBytes = Files.readAllBytes(output);
//        byte[] expectedBytes = Files.readAllBytes(expected);
//
//        assertArrayEquals(
//                expectedBytes,
//                actualBytes);
    }
}
