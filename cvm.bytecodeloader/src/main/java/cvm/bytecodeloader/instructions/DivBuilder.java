package cvm.bytecodeloader.instructions;

import cvm.instructions.arithmetic.Div;
import cvm.instructions.AbstractVmInstruction;
import utils.InstrBuilder;

/**
 * <b>DivBuilder</b>
 * InstructionBuilder. Builds division instruction.
 *
 * @link Uses instruction #{@link Div}
 * @bytecode div
 */
@InstrBuilder("div")
public class DivBuilder extends InstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Div(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
