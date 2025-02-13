package cvm.parser.instructions;

import cvm.instructions.Log;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

/**
 * <b>Log</b>
 * InstructionBuilder. Builds log instruction.
 *
 * @link Uses instruction #{@link Log}
 * @bytecode log
 */
@InstrBuilder("log")
public class LogBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct() {
        return new Log(ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
