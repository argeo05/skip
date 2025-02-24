package cvm.instructions;

import cvm.Context;

/**
 * <b>Log</b>
 * Instruction. Prints top stack value.
 *
 * @bytecode log
 * @stackArg value argument
 */

public final class Log implements VMInstruction {
    Context ctx;
    long value;

    public Log(Context ctx) {
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
