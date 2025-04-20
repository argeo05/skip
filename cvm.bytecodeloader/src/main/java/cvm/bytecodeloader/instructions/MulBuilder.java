package cvm.bytecodeloader.instructions;

import cvm.instructions.arithmetic.Mul;
import cvm.instructions.AbstractVmInstruction;
import utils.InstrBuilder;

/**
 * <b>MulBuilder</b>
 * InstructionBuilder. Builds multiplication instruction.
 *
 * @link Uses instruction #{@link Mul}
 * @bytecode mul
 */
@InstrBuilder("mul")
public class MulBuilder extends InstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Mul(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
