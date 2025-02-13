package cvm.instructions;

import cvm.Context;

/**
 * <b>Sub</b>
 * Instruction. Pops two args and pushes their subtraction.
 *
 * @bytecode sub
 * @stackArg lhs minuend
 * @stackArg rhs subtrahend
 */

public final class Sub extends BinaryInstruction {
    public Sub(Context ctx) {
        super(ctx);
    }

    @Override
    long calc() {
        return lhs - rhs;
    }
}
