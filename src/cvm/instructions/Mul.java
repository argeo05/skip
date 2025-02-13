package cvm.instructions;

import cvm.Context;

/**
 * <b>Mul</b>
 * Instruction that gives a product.
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
