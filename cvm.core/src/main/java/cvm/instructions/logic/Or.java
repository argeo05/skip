package cvm.instructions.logic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Or</b>
 * instruction. Pops two arguments and pushes 1 if at least one operand is non-zero, otherwise 0.
 *
 * @bytecode or
 * @opcode 15
 * @stackArg lhs first operand
 * @stackArg rhs second operand
 */
public final class Or extends AbstractBinaryInstructionAbstract {
    public Or(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    protected long calc() {
        return (lhs != 0 || rhs != 0) ? 1L : 0L;
    }
}
