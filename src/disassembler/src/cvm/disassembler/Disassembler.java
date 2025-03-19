package cvm.disassembler;

import cvm.instructions.Instructions;
import utils.BytesParser;
import java.io.*;
import java.util.Arrays;

public final class Disassembler {
    public static void disassemble(String inputPath, String outputPath) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            byte[] expectedMagic = new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65};
            byte[] magicBytes = new byte[4];
            dis.readFully(magicBytes);
            if (!Arrays.equals(magicBytes, expectedMagic)) {
                throw new IOException("Invalid magic bytes");
            }
            dis.skipBytes(30);
            writer.write("fun main\n");
            while (dis.available() > 0) {
                int opcode = dis.readUnsignedByte();
                int instrType = dis.readUnsignedByte();
                Instructions command = Instructions.fromOpcode(opcode);
                if (command == null) {
                    throw new IOException("Unknown opcode: " + opcode);
                }
                writer.write("    " + command.name());
                if (instrType == 0 && opcode == Instructions.LD.getOpcode()) {
                    byte[] argBytes = new byte[4];
                    dis.readFully(argBytes);
                    writer.write(" " + BytesParser.toDeciminal(argBytes));
                }
                writer.write("\n");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: must be 2 arguments.");
            return;
        }
        try {
            Disassembler.disassemble(args[0], args[1]);
            System.out.println("Disassembly completed");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
