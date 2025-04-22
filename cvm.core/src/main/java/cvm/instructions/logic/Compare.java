package cvm.instructions.logic;

import cvm.Context;
import cvm.instructions.AbstractBinaryInstructionAbstract;

/**
 * <b>Compare</b>
 * instruction. Pops two arguments and pushes 1 if the comparison holds, otherwise 0.
 * <p>Variants distinguished by the type byte:
 * 0 – less‑or‑equal (<=),
 * 1 – less (<),
 * 2 – greater‑or‑equal (>=),
 * 3 – greater (>).
 * </p>
 *
 * @bytecode compare
 * @opcode 12
 * @stackArg lhs first operand
 * @stackArg rhs second operand
 */
public final class Compare extends AbstractBinaryInstructionAbstract {
    public Compare(byte type, Context ctx) {
        super(type, ctx);
    }

    @Override
    protected long calc() {
        return switch (type) {
            case 0 -> lhs <= rhs ? 1L : 0L;
            case 1 -> lhs < rhs ? 1L : 0L;
            case 2 -> lhs >= rhs ? 1L : 0L;
            case 3 -> lhs > rhs ? 1L : 0L;
            default -> throw new IllegalArgumentException("Unknown compare type: " + type);
        };
    }
}
