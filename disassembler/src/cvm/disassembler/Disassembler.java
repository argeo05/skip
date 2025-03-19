package cvm.disassembler;

import cvm.instructions.Instructions;
import utils.BytesParser;
import utils.HexFileReader;

import java.io.*;
import java.util.Arrays;

public final class Disassembler {
    public static void disassemble(String inputPath, String outputPath) throws IOException {
        try (HexFileReader reader = new HexFileReader(inputPath);
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            byte[] expectedMagic = new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65};
            byte[] magicBytes = reader.readBytes(4);

            if (!Arrays.equals(magicBytes, expectedMagic)) {
                throw new IOException("Invalid magic bytes");
            }

            reader.readBytes(30);

            writer.write("fun main\n");

            while (reader.hasNext()) {
                int opcode = reader.readBytes(1)[0] & 0xFF;
                int instrType = reader.readBytes(1)[0] & 0xFF;

                Instructions command = Instructions.fromOpcode(opcode);
                if (command == null) {
                    throw new IOException("Unknown opcode: " + opcode);
                }

                writer.write("    " + command.name());

                if (instrType == 0 && (opcode == 0)) {
                    byte[] args = reader.readBytes(4);
                    writer.write(" " + BytesParser.toDeciminal(args));
                }

                writer.write("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String OUTPUT_PATH = "disassembler/resources/output.txt";
        final String INPUT_PATH = "disassembler/resources/input.txt";

        try {
            Disassembler.disassemble(INPUT_PATH, OUTPUT_PATH);
            System.out.println("Disassembly completed");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}