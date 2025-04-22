package cvm.instructions.arithmetic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Mod</b>
 * Instruction. Pops two args and pushes their remainder.
 *
 * @bytecode mod
 * @opcode 11
 * @stackArg lhs dividend
 * @stackArg rhs divisor
 */
public final class Mod extends AbstractBinaryInstructionAbstract {
    public Mod(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    public long calc() {
        return lhs % rhs;
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
