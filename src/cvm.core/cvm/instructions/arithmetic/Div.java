package cvm.instructions.arithmetic;

import cvm.Context;
import cvm.instructions.BinaryInstruction;

/**
 * <b>Div</b>
 * Instruction. Pops two args and pushes their division.
 *
 * @bytecode div
 * @opcode 4
 * @stackArg lhs dividend
 * @stackArg rhs divider
 */

public final class Div extends BinaryInstruction {
    public Div(Byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    long calc() {
        return lhs / rhs;
    }
}
