package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.arithmetic.Add;
import utils.InstrBuilder;

/**
 * <b>AddBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds add instruction.
 *
 * @link Uses instruction #{@link Add}
 * @bytecode add
 */
@InstrBuilder("add")
public class AddBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Add(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
