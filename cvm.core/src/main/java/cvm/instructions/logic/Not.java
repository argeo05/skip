package cvm.instructions.logic;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Not</b>
 * instruction. Pops one argument and pushes 1 if the operand is zero, otherwise 0.
 *
 * @bytecode not
 * @opcode 16
 * @stackArg value operand
 */
public final class Not extends AbstractVmInstruction {
    private final Context ctx;
    private long value;

    public Not(Byte type, Context ctx) {
        super(type);
        this.ctx = ctx;
    }

    @Override
    protected void popArgs() {
        value = ctx.pop();
    }

    @Override
    protected void run() {
        ctx.push(value == 0 ? 1L : 0L);
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
