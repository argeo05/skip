package cvm.bytecodeloader.instructions;

import cvm.instructions.Add;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

/**
 * <b>AddBuilder</b>
 * InstructionBuilder. Builds add instruction.
 *
 * @link Uses instruction #{@link Add}
 * @bytecode add
 */
@InstrBuilder("add")
public class AddBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct(byte type) {
        return new Add(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
