package cvm.bytecodeloader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class ByteCodeLoaderTest {

    @Test
    void testExecuteCompiledProgramOutputs12() throws Exception {
        Path resDir = Paths.get("src", "test", "resources");
        Path input = resDir.resolve("inputbytecodeloader.sosa");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        ByteCodeLoader.main(new String[]{ input.toString() });
        System.setOut(originalOut);

        String result = out.toString().trim();
        assertEquals("12", result);
    }
}