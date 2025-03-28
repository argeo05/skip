package cvm.bytecodeloader.instructions;

import cvm.instructions.special.Debug;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

/**
 * <b>DebugBuilder</b>
 * InstructionBuilder. Builds debug instruction.
 *
 * @link Uses instruction #{@link Debug}
 * @bytecode debug
 */
@InstrBuilder("debug")
public class DebugBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct(byte type) {
        return new Debug(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
