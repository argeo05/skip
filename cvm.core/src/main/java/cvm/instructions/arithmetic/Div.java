package cvm.instructions.arithmetic;

import cvm.Context;
import cvm.exceptions.DivisionByZeroException;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Div</b>
 * Instruction. Pops two args and pushes their division.
 *
 * @bytecode div
 * @opcode 4
 * @stackArg lhs dividend
 * @stackArg rhs divider
 */

public final class Div extends AbstractBinaryInstructionAbstract {
    public Div(Byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    public long calc() {
        if (rhs == 0) {
            throw new DivisionByZeroException();
        }
        return lhs / rhs;
    }

    public int getArgsCount() {
        return 0;
    }

}
