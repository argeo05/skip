package cvm.instructions.logic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Xor</b>
 * instruction. Pops two arguments and pushes 1 if exactly one operand is non-zero, otherwise 0.
 *
 * @bytecode xor
 * @opcode 17
 * @stackArg lhs first operand
 * @stackArg rhs second operand
 */
public final class Xor extends AbstractBinaryInstructionAbstract {
    public Xor(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    protected long calc() {
        boolean a = lhs != 0;
        boolean b = rhs != 0;
        return (a ^ b) ? 1L : 0L;
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
