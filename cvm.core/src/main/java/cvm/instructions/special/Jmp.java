package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Jmp</b> Instruction. Jump without condition.
 *
 * @bytecode jmp
 * @opcode 18
 */
public final class Jmp extends AbstractVmInstruction {
    private final Context ctx;
    private final int target;

    public Jmp(byte type, int target, Context ctx) {
        super(type);
        this.ctx = ctx;
        this.target = target;
    }

    @Override
    protected void popArgs() {
    }

    @Override
    protected void run() {
        ctx.jumpTo(target);
    }
}
