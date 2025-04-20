package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.arithmetic.Div;
import utils.InstrBuilder;

/**
 * <b>DivBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds division instruction.
 *
 * @link Uses instruction #{@link Div}
 * @bytecode div
 */
@InstrBuilder("div")
public class DivBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Div(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
