package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>JmpIf</b> Instruction. Jump with condition.
 * @bytecode jmpif
 * @opcode 19
 */
public final class JmpIf extends AbstractVmInstruction {
    private final Context ctx;
    private final int target;
    private long cond;

    public JmpIf(byte type, int target, Context ctx) {
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
            ctx.jumpTo(target);
        }
    }
}
