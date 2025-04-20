package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Load</b>
 * Instruction. Invokes function.
 *
 * @bytecode invoke
 * @opcode 8
 */
public final class Invoke extends AbstractVmInstruction {
    Context ctx;
    long value;

    public Invoke(byte type, long arg, Context ctx) {
        super(type);
        this.ctx = ctx;
        this.value = arg;
    }

    @Override
    protected void popArgs() { }

    @Override
    protected void run() {
        ctx.invoke(value);
    }
}
