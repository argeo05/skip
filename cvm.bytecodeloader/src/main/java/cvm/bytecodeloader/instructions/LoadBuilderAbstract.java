package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.special.Load;
import utils.InstrBuilder;

/**
 * <b>LoadBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds load instruction.
 *
 * @link Uses instruction #{@link Load}
 * @bytecode load
 */
@InstrBuilder("ld")
public class LoadBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    int getArgsCount() {
        return 1;
    }

    @Override
    public AbstractVmInstruction construct(byte type) {
        long value = Long.parseLong(args[0]);
        return new Load(type, value, ctx);
    }
}
