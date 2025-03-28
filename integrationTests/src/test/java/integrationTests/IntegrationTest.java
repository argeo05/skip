package integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import cvm.compiler.Compiler;
import cvm.disassembler.Disassembler;
import app.Main;

public class IntegrationTest {

    @Test
    public void testFullFlow() throws Exception {
        Path resDir = Path.of("src", "test", "resources");
        Path inputCompiler = resDir.resolve("inputcomplier.txt");
        Path outputCompiler = resDir.resolve("outputcomplier.sosa");
        Path outputDisassembler = resDir.resolve("outputdisassembler.txt");

        Files.deleteIfExists(outputCompiler);
        Files.deleteIfExists(outputDisassembler);

        String[] compilerArgs = { inputCompiler.toString(), outputCompiler.toString() };
        Compiler.main(compilerArgs);

        String[] disassemblerArgs = { outputCompiler.toString(), outputDisassembler.toString() };
        Disassembler.main(disassemblerArgs);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));
        String[] appArgs = { outputCompiler.toString() };
        Main.main(appArgs);
        System.setOut(originalOut);

        String output = baos.toString().trim();
        String expected = "12";
        assertEquals(expected, output, "The result of vm must be 12");
    }
}
