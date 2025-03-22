package cvm.instructions;

import cvm.Context;

public final class Get extends VMInstruction {
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
