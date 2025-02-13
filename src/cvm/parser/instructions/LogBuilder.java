package cvm.parser.instructions;

import cvm.instructions.Log;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

@InstrBuilder("log")
public class LogBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct() {
        return new Log(ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
