package cvm.disassembler;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cvm.instructions.Instructions;
import utils.BytesParser;

import static cvm.instructions.Instructions.requiresArgument;

/**
 * Utility for converting compiled bytecode files into a human-readable
 * assembly-like representation, включая метки для переходов.
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

            dis.skipBytes(6);
            int constantCount = (int) BytesParser.toDeciminal(dis.readNBytes(4));
            int functionCount = (int) BytesParser.toDeciminal(dis.readNBytes(4));
            dis.skipBytes(20);

            List<String> constants = new ArrayList<>();
            writer.write("const " + constantCount + "\n");
            for (int i = 0; i < constantCount; i++) {
                int type = dis.readUnsignedByte();
                writer.write("    " + i + " ");
                switch (type) {
                    case 1 -> { // INTEGER
                        int v = (int) BytesParser.toDeciminal(dis.readNBytes(4));
                        writer.write(v + "\n");
                        constants.add(String.valueOf(v));
                    }
                    case 2 -> { // FLOAT
                        int bits = (int) BytesParser.toDeciminal(dis.readNBytes(4));
                        float f = Float.intBitsToFloat(bits);
                        writer.write(f + "\n");
                        constants.add(String.valueOf(f));
                    }
                    case 3 -> { // STRING
                        int len = (int) BytesParser.toDeciminal(dis.readNBytes(4));
                        byte[] bs = dis.readNBytes(len);
                        String s = new String(bs, StandardCharsets.UTF_8);
                        writer.write("\"" + s + "\"\n");
                        constants.add(s);
                    }
                    default -> throw new IOException("Unknown constant type: " + type);
                }
            }
            writer.write("\n");

            for (int f = 0; f < functionCount; f++) {
                int nameLen = (int) BytesParser.toDeciminal(dis.readNBytes(4));
                String funName = new String(dis.readNBytes(nameLen), StandardCharsets.UTF_8);
                int argc = (int) BytesParser.toDeciminal(dis.readNBytes(4));
                int varCount = (int) BytesParser.toDeciminal(dis.readNBytes(4));
                int instrCount = (int) BytesParser.toDeciminal(dis.readNBytes(4));

                writer.write("fun " + funName + " " + argc + " " + varCount + "\n");

                class Instr {
                    String mnemonic;
                    int target = -1;
                    String rawArg = "";
                }

                List<Instr> instrs = new ArrayList<>(instrCount);
                Set<Integer> jumpTargets = new HashSet<>();

                for (int idx = 0; idx < instrCount; idx++) {
                    int opcode = dis.readUnsignedByte();
                    int instrType = dis.readUnsignedByte();
                    Instructions ins = Instructions.fromOpcode(opcode);
                    if (ins == null) {
                        throw new IOException("Unknown opcode: " + opcode);
                    }
                    String mnem = ins.name().toLowerCase();
                    if ("ld".equals(mnem) && instrType == 1) {
                        mnem = "ldc";
                    }

                    Instr rec = new Instr();
                    rec.mnemonic = mnem;

                    if (requiresArgument(opcode)) {
                        int arg = (int) BytesParser.toDeciminal(dis.readNBytes(4));
                        if (ins == Instructions.INVOKE && arg >= 0 && arg < constants.size()) {
                            rec.rawArg = "\"" + constants.get(arg) + "\"";
                        } else if (ins == Instructions.JMP || ins == Instructions.JMPIF) {
                            rec.target = arg;
                            jumpTargets.add(arg);
                        } else {
                            rec.rawArg = String.valueOf(arg);
                        }
                    }
                    instrs.add(rec);
                }

                Map<Integer, String> labelNames = new HashMap<>();
                for (Integer tgt : jumpTargets) {
                    labelNames.put(tgt, "L" + tgt);
                }

                for (int idx = 0; idx < instrs.size(); idx++) {
                    if (labelNames.containsKey(idx)) {
                        writer.write("    lb " + labelNames.get(idx) + "\n");
                    }
                    Instr ins = instrs.get(idx);
                    String argOut = "";
                    if (ins.target >= 0) {
                        argOut = " " + labelNames.get(ins.target);
                    } else if (!ins.rawArg.isEmpty()) {
                        argOut = " " + ins.rawArg;
                    }
                    writer.write("    " + ins.mnemonic + argOut + "\n");
                }
                writer.write("\n");
            }
        }
    }

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
