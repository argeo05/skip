package cvm.bytecodeloader;

import cvm.Context;
import cvm.Function;
import cvm.instructions.Instructions;
import cvm.instructions.VMInstruction;
import utils.BytesParser;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import static cvm.bytecodeloader.InstructionBuilderResolver.resolve;

public class ByteCodeLoader {
    private Function curr = null;
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
            byte[] expectedMagic = new byte[]{(byte)0x83, 0x79, (byte)0x83, 0x65};
            byte[] magicBytes = new byte[4];
            dis.readFully(magicBytes);
            if (!Arrays.equals(magicBytes, expectedMagic)) {
                throw new IOException("Invalid magic bytes");
            }
            dis.skipBytes(30);
            if (curr != null) {
                ctx.addFun(curr);
            }
            curr = new Function("main");
            while (dis.available() > 0) {
                int opcode = dis.readUnsignedByte();
                int instrType = dis.readUnsignedByte();
                String instruction = Instructions.fromOpcode(opcode).name().toLowerCase();
                String[] args;
                if (instrType == 0 && opcode == Instructions.LD.getOpcode()) {
                    byte[] argBytes = new byte[4];
                    dis.readFully(argBytes);
                    args = new String[]{String.valueOf(BytesParser.toDeciminal(argBytes))};
                } else {
                    args = new String[0];
                }
                VMInstruction res;
                try {
                    res = resolve(instruction).setCtx(ctx).setArgs(args).build((byte)0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                curr.addInstruction(res);
            }
            ctx.addFun(curr);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing binary bytecode: " + e.getMessage(), e);
        }
    }
}
