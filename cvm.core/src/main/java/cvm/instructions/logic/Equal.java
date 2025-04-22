package cvm.instructions.logic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Equal</b>
 * instruction. Pops two arguments and pushes 1 if they satisfy the equality test, otherwise 0.
 * <p>Variants distinguished by the type byte:
 * 0 – equal (==),
 * 1 – not equal (!=).
 * </p>
 *
 * @bytecode equal
 * @opcode 13
 * @stackArg lhs first operand
 * @stackArg rhs second operand
 */
public final class Equal extends AbstractBinaryInstructionAbstract {
    public Equal(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    protected long calc() {
        return type == 0
                ? (lhs == rhs ? 1L : 0L)
                : (lhs != rhs ? 1L : 0L);
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
