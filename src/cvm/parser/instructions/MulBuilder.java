package cvm.parser.instructions;

import cvm.instructions.Mul;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

@InstrBuilder("mul")
public class MulBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct() {
        return new Mul(ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
