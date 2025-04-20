package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.arithmetic.Mul;
import utils.InstrBuilder;

/**
 * <b>MulBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds multiplication instruction.
 *
 * @link Uses instruction #{@link Mul}
 * @bytecode mul
 */
@InstrBuilder("mul")
public class MulBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Mul(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
