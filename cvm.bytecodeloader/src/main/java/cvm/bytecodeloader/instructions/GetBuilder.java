package cvm.bytecodeloader.instructions;

import cvm.instructions.special.Get;
import cvm.instructions.AbstractVmInstruction;
import utils.InstrBuilder;

/**
 * <b>GetBuilder</b>
 * InstructionBuilder. Builds the get instruction.
 *
 * @link Uses instruction {@link Get}
 * @bytecode get
 */
@InstrBuilder("get")
public class GetBuilder extends InstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        long index = Long.parseLong(args[0]);
        return new Get(type, ctx, index);
    }

    @Override
    int getArgsCount() {
        return 1;
    }
}
