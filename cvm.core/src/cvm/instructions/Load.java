package cvm.instructions;

import cvm.Context;

/**
 * <b>Load</b>
 * Instruction. Puts argument into stack.
 *
 * @bytecode ld
 */
public final class Load extends VMInstruction {
    Context ctx;
    long value;

    public Load(byte type, long value, Context ctx) {
        super(type);
        this.value = value;
        this.ctx = ctx;
    }

    @Override
    public void popArgs() {

    }

    @Override
    public void run() {
        ctx.push(value);
    }
}
