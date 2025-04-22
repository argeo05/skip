package cvm.disassembler;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisassemblerTest {

    @Test
    public void testDisassembleOutput() throws IOException {
        // Arrange: locate resource files
        Path resDir = Paths.get("src", "test", "resources");
        Path input = resDir.resolve("inputdisassembler.sosa");
        Path expected = resDir.resolve("outputdisassembler.txt");
        Path actual = resDir.resolve("actual_disassembled.txt");

        Files.deleteIfExists(actual);

        Disassembler.disassemble(input.toString(), actual.toString());

        String actualContent = Files.readString(actual);
        String expectedContent = Files.readString(expected);
        assertEquals(expectedContent, actualContent, "Disassembled output should match expected file");
    }
}
