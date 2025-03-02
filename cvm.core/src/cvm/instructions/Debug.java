package cvm.instructions;

import cvm.Context;

/**
 * <b>Debug</b>
 * Instruction. Pops last stack value and prints it.
 *
 * @bytecode debug
 */
public final class Debug extends VMInstruction {
    Context ctx;

    public Debug(Byte type, Context ctx) {
        super(type);
        this.ctx = ctx;
    }

    @Override
    public void popArgs() {
    }

    @Override
    public void run() {
        System.out.println(ctx.top());
    }
}
