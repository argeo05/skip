package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

public final class Get extends AbstractVmInstruction {
    Context ctx;
    long ind;

    public Get(Byte type, Context ctx, long ind) {
        super(type);
        this.ctx = ctx;
        this.ind = ind;
    }

    @Override
    public void popArgs() { }

    public void run() {
        ctx.push(ctx.getVar(ind));
    }
}
