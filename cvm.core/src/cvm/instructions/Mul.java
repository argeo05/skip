package cvm.instructions;

import cvm.Context;

/**
 * <b>Mul</b>
 * Instruction. Pops last stack value and pushes their product.
 *
 * @bytecode mul
 * @stackArg lhs first multiplier
 * @stackArg rhs second multiplier
 */

public final class Mul extends BinaryInstruction {
    public Mul(Context ctx) {
        super(ctx);
    }

    @Override
    long calc() {
        return lhs * rhs;
    }
}
