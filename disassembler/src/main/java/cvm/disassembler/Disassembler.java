package cvm.disassembler;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cvm.instructions.Instructions;
import utils.BytesParser;

/**
 * Utility for converting compiled bytecode files into a human-readable
 * assembly-like representation, including constants and instructions.
 */
public final class Disassembler {

    /**
     * Reads a binary bytecode file from the given input path, disassembles
     * its contents into textual form, and writes the result to the specified
     * output path.
     *
     * <p>The output format begins with a list of constants, then for each
     * function emits its signature and indented instructions.</p>
     *
     * @param inputPath  the file system path to the bytecode input file
     * @param outputPath the file system path where the disassembled text will be written
     * @throws IOException if an I/O error occurs during reading or writing
     */
    public static void disassemble(String inputPath, String outputPath) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            byte[] expectedMagic = new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65};
            byte[] magic = new byte[4];
            dis.readFully(magic);
            if (!Arrays.equals(magic, expectedMagic)) {
                throw new IOException("Invalid magic bytes");
            }

            byte[] versionBytes = new byte[6];
            dis.readFully(versionBytes);

            byte[] constCountBytes = new byte[4];
            dis.readFully(constCountBytes);
            int constantCount = (int) BytesParser.toDeciminal(constCountBytes);

            byte[] funCountBytes = new byte[4];
            dis.readFully(funCountBytes);
            int functionCount = (int) BytesParser.toDeciminal(funCountBytes);

            dis.skipBytes(20);

            List<String> constants = new ArrayList<>();
            writer.write("const " + constantCount + "\n");
            for (int i = 0; i < constantCount; i++) {
                int type = dis.readUnsignedByte();
                String value;
                byte[] data;
                switch (type) {
                    case 1: // INTEGER
                        data = new byte[4];
                        dis.readFully(data);
                        int intValue = (int) BytesParser.toDeciminal(data);
                        value = String.valueOf(intValue);
                        break;
                    case 2: // FLOAT
                        data = new byte[4];
                        dis.readFully(data);
                        int bits = (int) BytesParser.toDeciminal(data);
                        float f = Float.intBitsToFloat(bits);
                        value = String.valueOf(f);
                        break;
                    case 3: // STRING
                        byte[] lenBytes = new byte[4];
                        dis.readFully(lenBytes);
                        int len = (int) BytesParser.toDeciminal(lenBytes);
                        byte[] strBytes = new byte[len];
                        dis.readFully(strBytes);
                        value = "\"" + new String(strBytes, "UTF-8") + "\"";
                        break;
                    default:
                        throw new IOException("Unknown constant type: " + type);
                }
                constants.add(value);
                writer.write("    " + i + " " + value + "\n");
            }

            writer.write("\n");

            for (int f = 0; f < functionCount; f++) {
                byte[] nameLenBytes = new byte[4];
                dis.readFully(nameLenBytes);
                int nameLen = (int) BytesParser.toDeciminal(nameLenBytes);
                byte[] nameBytes = new byte[nameLen];
                dis.readFully(nameBytes);
                String funName = new String(nameBytes, "UTF-8");

                byte[] argcBytes = new byte[4];
                dis.readFully(argcBytes);
                int argc = (int) BytesParser.toDeciminal(argcBytes);

                byte[] varCountBytes = new byte[4];
                dis.readFully(varCountBytes);
                int varCount = (int) BytesParser.toDeciminal(varCountBytes);

                byte[] instrCountBytes = new byte[4];
                dis.readFully(instrCountBytes);
                int instrCount = (int) BytesParser.toDeciminal(instrCountBytes);

                writer.write("fun " + funName + " " + argc + " " + varCount + "\n");

                for (int j = 0; j < instrCount; j++) {
                    int opcode = dis.readUnsignedByte();
                    int instrType = dis.readUnsignedByte();

                    Instructions ins = Instructions.fromOpcode(opcode);
                    if (ins == null) {
                        throw new IOException("Unknown opcode: " + opcode);
                    }
                    String mnemonic = ins.name().toLowerCase();
                    if (mnemonic.equals("ld") && instrType == 1) {
                        mnemonic = "ldc";
                    }

                    String argStr = "";
                    if (requiresArgument(opcode)) {
                        byte[] argBytes = new byte[4];
                        dis.readFully(argBytes);
                        int arg = (int) BytesParser.toDeciminal(argBytes);
                        if (ins == Instructions.INVOKE && arg >= 0 && arg < constants.size()) {
                            argStr = " " + constants.get(arg);
                        } else {
                            argStr = " " + arg;
                        }
                    }
                    writer.write("    " + mnemonic + argStr + "\n");
                }
                writer.write("\n");
            }
        }
    }

    // private helper omitted from Javadoc
    private static boolean requiresArgument(int opcode) {
        return opcode == Instructions.LD.getOpcode()
                ||
                opcode == Instructions.GET.getOpcode()
                ||
                opcode == Instructions.PUT.getOpcode()
                ||
                opcode == Instructions.INVOKE.getOpcode();
    }

    /**
     * Entry point for command-line usage. Expects two arguments:
     * the input bytecode file path and the output text file path.
     * Prints usage instructions or error details to standard error.
     *
     * @param args command-line arguments: [inputPath, outputPath]
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: Disassembler <inputPath> <outputPath>");
            return;
        }
        try {
            disassemble(args[0], args[1]);
            System.out.println("Disassembly completed");
        } catch (IOException e) {
            System.err.println("Error during disassembly: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
