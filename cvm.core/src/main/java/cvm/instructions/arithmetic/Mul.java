package cvm.instructions.arithmetic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Mul</b>
 * Instruction. Pops last stack value and pushes their product.
 *
 * @bytecode mul
 * @opcode 3
 * @stackArg lhs first multiplier
 * @stackArg rhs second multiplier
 */

public final class Mul extends AbstractBinaryInstructionAbstract {
    public Mul(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    public long calc() {
        return lhs * rhs;
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
