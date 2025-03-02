package cvm.instructions;

import cvm.Context;

/**
 * <b>Div</b>
 * Instruction. Pops two args and pushes their division.
 *
 * @bytecode div
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
