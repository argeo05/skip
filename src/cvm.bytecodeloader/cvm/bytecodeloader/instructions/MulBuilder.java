package cvm.bytecodeloader.instructions;

import cvm.instructions.arithmetic.Mul;
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
    VMInstruction construct(byte type) {
        return new Mul(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
