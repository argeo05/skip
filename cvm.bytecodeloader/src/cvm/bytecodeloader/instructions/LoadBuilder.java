package cvm.bytecodeloader.instructions;

import cvm.instructions.Load;
import cvm.instructions.VMInstruction;
import utils.InstrBuilder;

/**
 * <b>LoadBuilder</b>
 * InstructionBuilder. Builds load instruction.
 *
 * @link Uses instruction #{@link Load}
 * @bytecode load
 */
@InstrBuilder("ld")
public class LoadBuilder extends InstructionBuilder {
    @Override
    int getArgsCount() {
        return 1;
    }

    @Override
    public VMInstruction construct(byte type) {
        long value = Long.parseLong(args[0]);
        return new Load(type, value, ctx);
    }
}
