package cvm.instructions.logic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>And</b>
 * instruction. Pops two arguments and pushes 1 if both operands are non-zero, otherwise 0.
 *
 * @bytecode and
 * @opcode 14
 * @stackArg lhs first operand
 * @stackArg rhs second operand
 */
public final class And extends AbstractBinaryInstructionAbstract {
    public And(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    protected long calc() {
        return (lhs != 0 && rhs != 0) ? 1L : 0L;
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
