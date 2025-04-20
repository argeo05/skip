package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.special.Debug;
import utils.InstrBuilder;

/**
 * <b>DebugBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds debug instruction.
 *
 * @link Uses instruction #{@link Debug}
 * @bytecode debug
 */
@InstrBuilder("debug")
public class DebugBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        return new Debug(type, ctx);
    }

    @Override
    int getArgsCount() {
        return 0;
    }
}
