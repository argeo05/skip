package cvm.parser.instructions;

import cvm.instructions.Add;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

/**
 * <b>Add</b>
 * InstructionBuilder. Builds add instruction.
 *
 * @link Uses instruction #{@link Add}
 * @bytecode add
 */
@InstrBuilder("add")
public class AddBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct() {
        return new Add(ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
