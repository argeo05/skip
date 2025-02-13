package cvm.instructions;

import cvm.Context;

/**
 * <b>Debug</b>
 * Instruction. Pops last stack value and prints it.
 *
 * @bytecode debug
 */
public final class Debug implements VMInstruction {
    Context ctx;
    long value;

    public Debug(Context ctx) {
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
