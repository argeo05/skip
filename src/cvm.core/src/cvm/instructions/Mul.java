package cvm.instructions;

import cvm.Context;

/**
 * <b>Mul</b>
 * Instruction. Pops last stack value and pushes their product.
 *
 * @bytecode mul
 * @opcode 3
 * @stackArg lhs first multiplier
 * @stackArg rhs second multiplier
 */

public final class Mul extends BinaryInstruction {
    public Mul(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    long calc() {
        return lhs * rhs;
    }
}
