package cvm.instructions.arithmetic;

import cvm.Context;
import cvm.instructions.BinaryInstruction;

/**
 * <b>Sub</b>
 * Instruction. Pops two args and pushes their subtraction.
 *
 * @bytecode sub
 * @opcode 2
 * @stackArg lhs minuend
 * @stackArg rhs subtrahend
 */

public final class Sub extends BinaryInstruction {
    public Sub(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    long calc() {
        return lhs - rhs;
    }
}
