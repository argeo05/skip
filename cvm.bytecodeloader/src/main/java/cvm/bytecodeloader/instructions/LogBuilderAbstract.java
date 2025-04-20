package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.special.Log;
import utils.InstrBuilder;

/**
 * <b>LogBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds log instruction.
 *
 * @link Uses instruction #{@link Log}
 * @bytecode log
 */
@InstrBuilder("log")
public class LogBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Log(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
