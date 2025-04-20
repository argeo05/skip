package cvm.bytecodeloader.instructions;

import cvm.instructions.special.Log;
import cvm.instructions.AbstractVmInstruction;
import utils.InstrBuilder;

/**
 * <b>LogBuilder</b>
 * InstructionBuilder. Builds log instruction.
 *
 * @link Uses instruction #{@link Log}
 * @bytecode log
 */
@InstrBuilder("log")
public class LogBuilder extends InstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Log(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
