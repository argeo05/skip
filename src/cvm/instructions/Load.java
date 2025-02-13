package cvm.instructions;

import cvm.Context;

/**
 * <b>Load</b>
 * Instruction. Puts argument into stack.
 *
 * @bytecode load
 */
public final class Load implements VMInstruction {
    Context ctx;
    long value;

    public Load(long value, Context ctx) {
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
