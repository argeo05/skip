package cvm.bytecodeloader.instructions;

import java.util.Arrays;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>AbstractInstructionBuilder</b>
 * AbstractInstructionBuilder. Builds a generic instruction.
 *
 * @link Uses instruction #{@link AbstractVmInstruction}
 */
public abstract class AbstractInstructionBuilder {
    protected Context ctx;

    protected String[] args;

    abstract AbstractVmInstruction construct(byte type);

    abstract int getArgsCount();

    public AbstractInstructionBuilder setArgs(String... args) {
        this.args = args;
        return this;
    }

    public AbstractInstructionBuilder setCtx(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    public AbstractVmInstruction build(byte type) {
        if (args.length != getArgsCount()) {
            throw new IllegalStateException(
                    "[" + this.getClass().getName() + "] Invalid arg count: "
                            + Arrays.toString(args)
            );
        }

        return construct(type);
    }
}
