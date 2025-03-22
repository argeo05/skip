package cvm.instructions;

import cvm.Context;

public final class Put extends VMInstruction {
    Context ctx;
    long ind;
    long value;

    public Put(Byte type, Context ctx, long ind) {
        super(type);
        this.ctx = ctx;
        this.ind = ind;
    }

    @Override
    public void popArgs() {
        value = ctx.pop();
    }

    public void run() {
        ctx.setVar(ind, value);
    }
}
