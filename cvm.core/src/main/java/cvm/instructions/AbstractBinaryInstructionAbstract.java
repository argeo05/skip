
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
public abstract sealed class AbstractBinaryInstructionAbstract extends AbstractVmInstruction permits Add, Div, Mul, Sub {
    protected Context ctx;
    protected long rhs;
    protected long lhs;

    public AbstractBinaryInstructionAbstract(byte type, Context ctx) {
        super(type);
        this.ctx = ctx;
    }

    protected abstract long calc();

    @Override
    public void popArgs() {
        rhs = ctx.pop();
        lhs = ctx.pop();
    }

    public void run() {
        ctx.push(calc());
    }
}
