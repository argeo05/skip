package cvm.parser.instructions;

import cvm.instructions.Debug;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

@InstrBuilder("debug")
public class DebugBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct() {
        return new Debug(ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
