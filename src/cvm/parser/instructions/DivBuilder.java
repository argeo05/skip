package cvm.parser.instructions;

import cvm.instructions.Div;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

@InstrBuilder("div")
public class DivBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct() {
        return new Div(ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
