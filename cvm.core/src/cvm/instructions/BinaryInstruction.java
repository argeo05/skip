
package cvm.instructions;

import cvm.Context;

/**
 * <b>BinaryInstruction</b>
 * Class for implements binary instructions to Virtual Machine.
 */
public abstract sealed class BinaryInstruction implements VMInstruction permits Add, Div, Mul, Sub {
    protected Context ctx;
    protected long rhs;
    protected long lhs;

    public BinaryInstruction(Context ctx) {
        this.ctx = ctx;
    }

    abstract long calc();

    @Override
    public void popArgs() {
        rhs = ctx.pop();
        lhs = ctx.pop();
    }

    public void run() {
        ctx.push(calc());
    }
}
