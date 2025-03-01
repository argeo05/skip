package cvm.bytecodeloader;

import cvm.Context;
import cvm.Function;
import cvm.instructions.VMInstruction;

import static cvm.bytecodeloader.InstructionBuilderResolver.resolve;
import static java.util.Arrays.copyOfRange;

public class ByteCodeLoader {
    String code;
    private Function curr = null;
    Context ctx;

    public ByteCodeLoader setCode(String code) {
        this.code = code;
        return this;
    }

    public ByteCodeLoader setCtx(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    public void parse() {
        code.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(s -> s.split(" "))
                .forEach(instr -> {
                    switch (instr[0]) {
                        case "fun" -> {
                            if (curr != null) {
                                ctx.addFun(curr);
                            }

                            String name = instr[1];
                            curr = new Function(name);
                        }
                        case ";" -> { /* Comment */ }
                        default -> {
                            VMInstruction res = null;
                            try {
                                res = resolve(instr[0])
                                        .setCtx(ctx)
                                        .setArgs(copyOfRange(instr, 1, instr.length))
                                        .build();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            curr.addInstruction(res);
                        }
                    }
                });

        ctx.addFun(curr);
    }
}
