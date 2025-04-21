package cvm.compiler;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class CompilerTest {
    @Test
    void testCompileOutput() throws Exception {
        Path resDir = Paths.get("src", "test", "resources");
        Path input = resDir.resolve("inputcomplier.txt");
        Path expected = resDir.resolve("outputcomplier.sosa");
        Path output = resDir.resolve("actual.sosa");

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
}
