package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

public final class Put extends AbstractVmInstruction {
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
