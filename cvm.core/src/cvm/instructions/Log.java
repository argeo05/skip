package cvm.instructions;

import cvm.Context;

/**
 * <b>Log</b>
 * Instruction. Prints top stack value.
 *
 * @bytecode log
 * @opcode 5
 * @stackArg value argument
 */

public final class Log extends VMInstruction {
    Context ctx;
    long value;

    public Log(Byte type, Context ctx) {
        super(type);
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
