package cvm.complier;

import cvm.instructions.Instructions;
import utils.BytesParser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compiler {
    private final StringBuilder output = new StringBuilder();

    public void compile(String sourcePath, String outputPath) throws IOException {
        String source = Files.readString(Path.of(sourcePath));

        writeHeader();

        compileToBytecode(source);

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(outputPath))) {
            writer.write(output.toString());
        }
    }

    private void writeHeader() {
        appendHexBytes(new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65});
        appendHexBytes(new byte[]{0x00, 0x01, 0x00, 0x00});
        appendHexBytes(new byte[4]);
        appendHexBytes(new byte[20]);
    }

    private void compileToBytecode(String source) {
        for (String line : source.split("\n")) {
            line = line.replaceAll(";.*", "").trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("fun ")) {
                /* WIP */
            } else {
                String[] parts = line.split("\\s+");
                String mnemonic = parts[0].toUpperCase();
                String arg = parts.length > 1 ? parts[1] : null;

                Instructions instr = Instructions.valueOf(mnemonic);
                writeInstruction(instr, arg);
            }
        }
    }

    private void writeInstruction(Instructions instr, String arg) {
        output.append(String.format("%02X ", instr.getOpcode()));
        output.append("00 ");

        if (instr == Instructions.LD && arg != null) {
            long value = Long.parseLong(arg);
            appendHexBytes(BytesParser.toBytes(value, 4));
        }
    }

    private void appendHexBytes(byte[] bytes) {
        for (byte b : bytes) {
            output.append(String.format("%02X ", b));
        }
    }

    public static void main(String[] args) {
        try {
            new Compiler().compile(
                    "compiler/resources/input.txt",
                    "compiler/resources/output.txt"
            );
            System.out.println("Compilation successful");
        } catch (Exception e) {
            System.err.println("Compilation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}