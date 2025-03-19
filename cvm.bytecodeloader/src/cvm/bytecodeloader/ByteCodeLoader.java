package cvm.bytecodeloader;

import cvm.Context;
import cvm.Function;
import cvm.instructions.Instructions;
import cvm.instructions.VMInstruction;
import utils.BytesParser;
import utils.HexFileReader;

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
        try (HexFileReader reader = new HexFileReader(inputPath)) {

            byte[] expectedMagic = new byte[]{(byte) 0x83, 0x79, (byte) 0x83, 0x65};
            byte[] magicBytes = reader.readBytes(6);

            if (!Arrays.equals(magicBytes, expectedMagic)) {
                throw new IOException("Invalid magic bytes");
            }

            reader.readBytes(28);

            if (curr != null) {
                ctx.addFun(curr);
            }
            curr = new Function("main");
            

            while (reader.hasNext()) {
                int opcode = reader.readBytes(1)[0] & 0xFF;
                int instrType = reader.readBytes(1)[0] & 0xFF;

                String instruction = Instructions.fromOpcode(opcode).name().toLowerCase();

                byte[] byteArgs;
                String[] args = null;
                if (instrType == 0 && (opcode == 0)) {
                    byteArgs = reader.readBytes(4);
                    args = new String[]{String.valueOf(BytesParser.toDeciminal(byteArgs))};
                } else {
                    args = new String[0];
                }

                VMInstruction res = null;
                try {
                    res = resolve(instruction)
                            .setCtx(ctx)
                            .setArgs(args)
                            .build((byte) 0);
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