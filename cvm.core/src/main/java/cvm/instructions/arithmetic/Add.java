package cvm.instructions.arithmetic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Add</b>
 * instruction. Pops two arguments and pushes their sum.
 *
 * @bytecode add
 * @opcode 1
 * @stackArg lhs first summand
 * @stackArg rhs second summand
 */

public final class Add extends AbstractBinaryInstructionAbstract {
    public static byte opcode = 1;

    public Add(byte type, Context ctx) {
        super(type, ctx);
    }

    public long calc() {
        return lhs + rhs;
    }
}
