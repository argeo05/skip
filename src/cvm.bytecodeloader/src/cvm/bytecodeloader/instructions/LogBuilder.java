package cvm.bytecodeloader.instructions;

import cvm.instructions.Log;
import cvm.instructions.VMInstruction;
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
    VMInstruction construct(byte type) {
        return new Log(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
