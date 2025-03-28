package cvm.bytecodeloader.instructions;

import cvm.Context;
import cvm.instructions.VMInstruction;

import java.util.Arrays;

/**
 * <b>InstructionBuilder</b>
 * InstructionBuilder. Builds a generic instruction.
 *
 * @link Uses instruction #{@link VMInstruction}
 */
public abstract class InstructionBuilder {
    protected Context ctx;
    protected String[] args;

    abstract VMInstruction construct(byte type);
    abstract int getArgsCount();

    public InstructionBuilder setArgs(String... args) {
        this.args = args;
        return this;
    }

    public InstructionBuilder setCtx(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    public VMInstruction build(byte type) {
        if (args.length != getArgsCount()) {
            throw new IllegalStateException(
                    "[" + this.getClass().getName() +  "] Invalid arg count: "
                            + Arrays.toString(args)
            );
        }

        return construct(type);
    }
}
