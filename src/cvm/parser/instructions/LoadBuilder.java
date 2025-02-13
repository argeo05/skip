package cvm.parser.instructions;

import cvm.instructions.Load;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

@InstrBuilder("ld")
public class LoadBuilder extends InstructionBuilder {
    @Override
    int getArgsCount() {
        return 1;
    }

    @Override
    public VMInstruction construct() {
        long value = Long.parseLong(args[0]);
        return new Load(value, ctx);
    }
}
