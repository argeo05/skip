package cvm.parser.instructions;

import cvm.instructions.Sub;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

@InstrBuilder("sub")
public class SubBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct() {
        return new Sub(ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
