package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.arithmetic.Sub;
import utils.InstrBuilder;

/**
 * <b>SubBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds subtraction instruction.
 *
 * @link Uses instruction #{@link Sub}
 * @bytecode sub
 */
@InstrBuilder("sub")
public class SubBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Sub(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
