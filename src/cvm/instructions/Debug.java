package cvm.instructions;

import cvm.Context;

/**
 * <b>Debug</b>
 * Instruction that prints last stack value.
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
        value = ctx.pop();
    }

    @Override
    public void run() {
        System.out.println(value);
    }
}
