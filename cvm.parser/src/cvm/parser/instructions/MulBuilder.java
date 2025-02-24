package cvm.parser.instructions;

import cvm.instructions.Mul;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

/**
 * <b>MulBuilder</b>
 * InstructionBuilder. Builds multiplication instruction.
 *
 * @link Uses instruction #{@link Mul}
 * @bytecode mul
 */
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
