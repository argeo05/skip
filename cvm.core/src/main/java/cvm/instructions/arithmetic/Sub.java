package cvm.instructions.arithmetic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Sub</b>
 * Instruction. Pops two args and pushes their subtraction.
 *
 * @bytecode sub
 * @opcode 2
 * @stackArg lhs minuend
 * @stackArg rhs subtrahend
 */

public final class Sub extends AbstractBinaryInstructionAbstract {
    public Sub(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    public long calc() {
        return lhs - rhs;
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
