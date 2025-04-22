package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Return</b> Instruction. Pops one value and завершает текущую функцию.
 * @bytecode ret
 * @opcode 20
 */
public final class Return extends AbstractVmInstruction {
    private final Context ctx;
    private long value;

    public Return(byte type, Context ctx) {
        super(type);
        this.ctx = ctx;
    }

    @Override
    protected void popArgs() {
        value = ctx.pop();
    }

    @Override
    protected void run() {
        ctx.returnFromFunction(value);
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
