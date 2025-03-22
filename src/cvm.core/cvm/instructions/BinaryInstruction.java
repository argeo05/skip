
package cvm.instructions;

import cvm.Context;
import cvm.instructions.arithmetic.Add;
import cvm.instructions.arithmetic.Div;
import cvm.instructions.arithmetic.Mul;
import cvm.instructions.arithmetic.Sub;

/**
 * <b>BinaryInstruction</b>
 * Class for implements binary instructions to Virtual Machine.
 */
public abstract sealed class BinaryInstruction extends VMInstruction permits Add, Div, Mul, Sub {
    protected Context ctx;
    protected long rhs;
    protected long lhs;

    public BinaryInstruction(byte type, Context ctx) {
        super(type);
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
