package cvm.bytecodeloader;

import cvm.Context;
import cvm.Function;
import cvm.instructions.Instructions;
import cvm.instructions.VMInstruction;
import utils.BytesParser;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static cvm.bytecodeloader.InstructionBuilderResolver.resolve;

public class ByteCodeLoader {
    private String inputPath;
    private Context ctx;

    public ByteCodeLoader setInputPath(String inputPath) {
        this.inputPath = inputPath;
        return this;
    }

    public ByteCodeLoader setCtx(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    public void parse() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputPath))) {
            byte[] expectedMagic = new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65};
            byte[] magicBytes = new byte[4];

            dis.readFully(magicBytes);
            if (!Arrays.equals(magicBytes, expectedMagic)) {
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

            List<String> constantTable = new ArrayList<>(constantCount);
            for (int i = 0; i < constantCount; i++) {
                int type = dis.readUnsignedByte();
                String value;
                if (type == 1) {
                    byte[] data = new byte[4];
                    dis.readFully(data);
                    int intValue = (int) BytesParser.toDeciminal(data);
                    value = String.valueOf(intValue);
                } else if (type == 2) {
                    byte[] data = new byte[4];
                    dis.readFully(data);
                    int bits = (int) BytesParser.toDeciminal(data);
                    float f = Float.intBitsToFloat(bits);
                    value = String.valueOf(f);
                } else if (type == 3) {
                    byte[] lenBytes = new byte[4];
                    dis.readFully(lenBytes);
                    int len = (int) BytesParser.toDeciminal(lenBytes);
                    byte[] strBytes = new byte[len];
                    dis.readFully(strBytes);
                    value = new String(strBytes, "UTF-8");
                } else {
                    throw new IOException("Unknown constant type: " + type);
                }
                constantTable.add(value);
                ctx.addConst(value);
            }
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
                Function fun = new Function(funName, argc, varCount);
                for (int j = 0; j < instrCount; j++) {
                    int opcode = dis.readUnsignedByte();
                    int instrType = dis.readUnsignedByte();
                    String mnemonic = Instructions.fromOpcode(opcode).name().toLowerCase();
                    String[] args;
                    if (requiresArgument(opcode)) {
                        byte[] argBytes = new byte[4];
                        dis.readFully(argBytes);
                        int arg = (int) BytesParser.toDeciminal(argBytes);
                        if ("invoke".equals(mnemonic)) {
                            args = new String[]{"#" + arg};
                        } else {
                            args = new String[]{String.valueOf(arg)};
                        }
                    } else {
                        args = new String[0];
                    }
                    VMInstruction instruction;
                    try {
                        instruction = resolve(mnemonic)
                                .setCtx(ctx)
                                .setArgs(args)
                                .build((byte) instrType);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    fun.addInstruction(instruction);
                }
                ctx.addFun(fun);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing binary bytecode: " + e.getMessage(), e);
        }
    }

    private boolean requiresArgument(int opcode) {
        return opcode == Instructions.LD.getOpcode() ||
                opcode == Instructions.GET.getOpcode() ||
                opcode == Instructions.PUT.getOpcode() ||
                opcode == Instructions.INVOKE.getOpcode();
    }

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("It is necessary to specify 1 argument - the path to the binary file");
            }
            Context ctx = new Context();
            new ByteCodeLoader().setInputPath(args[0]).setCtx(ctx).parse();
            ctx.start();
        } catch (Exception e) {
            System.err.println("Parsing failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
