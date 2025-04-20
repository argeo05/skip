package cvm.bytecodeloader.instructions;

import cvm.instructions.arithmetic.Sub;
import cvm.instructions.AbstractVmInstruction;
import utils.InstrBuilder;

/**
 * <b>SubBuilder</b>
 * InstructionBuilder. Builds subtraction instruction.
 *
 * @link Uses instruction #{@link Sub}
 * @bytecode sub
 */
@InstrBuilder("sub")
public class SubBuilder extends InstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Sub(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
