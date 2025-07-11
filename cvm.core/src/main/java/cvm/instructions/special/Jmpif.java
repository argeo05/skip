package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Jmpif</b> Instruction. Jump with condition.
 *
 * @bytecode jmpif
 * @opcode 19
 */
public final class Jmpif extends AbstractVmInstruction {
    private final Context ctx;
    private final long target;
    private long cond;

    public Jmpif(byte type, long target, Context ctx) {
        super(type);
        this.ctx = ctx;
        this.target = target;
    }

    @Override
    protected void popArgs() {
        cond = ctx.pop();
    }

    @Override
    protected void run() {
        if (cond != 0) {
            ctx.jumpTo((int) target);
        }
    }

    @Override
    public int getArgsCount() {
        return 1;
    }
}
