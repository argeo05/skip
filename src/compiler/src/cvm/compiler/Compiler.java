package cvm.compiler;

import cvm.instructions.Instructions;
import utils.BytesParser;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compiler {
    public void compile(String sourcePath, String outputPath) throws IOException {
        String source = Files.readString(Path.of(sourcePath));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        writeHeader(output);
        compileToBytecode(source, output);
        Files.write(Path.of(outputPath), output.toByteArray());
    }

    private void writeHeader(ByteArrayOutputStream output) throws IOException {
        output.write(new byte[]{(byte)0x83, 0x79, (byte)0x83, 0x65});
        output.write(new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x00});
        output.write(new byte[4]);
        output.write(new byte[20]);
    }

    private void compileToBytecode(String source, ByteArrayOutputStream output) throws IOException {
        for (String line : source.split("\n")) {
            line = line.replaceAll(";.*", "").trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("fun ")) continue;
            String[] parts = line.split("\\s+");
            String mnemonic = parts[0].toUpperCase();
            String arg = parts.length > 1 ? parts[1] : null;
            Instructions instr = Instructions.valueOf(mnemonic);
            writeInstruction(output, instr, arg);
        }
    }

    private void writeInstruction(ByteArrayOutputStream output, Instructions instr, String arg) throws IOException {
        output.write(instr.getOpcode());
        output.write(0);
        if (instr == Instructions.LD && arg != null) {
            long value = Long.parseLong(arg);
            output.write(BytesParser.toBytes(value, 4));
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("There must be 2 arguments");
            }

            new Compiler().compile(args[0], args[1]);
            System.out.println("Compilation successful");
        } catch (Exception e) {
            System.err.println("Compilation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
