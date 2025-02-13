package cvm.parser.instructions;

import cvm.instructions.Add;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

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
