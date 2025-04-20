package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.special.Get;
import utils.InstrBuilder;

/**
 * <b>GetBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds the get instruction.
 *
 * @link Uses instruction {@link Get}
 * @bytecode get
 */
@InstrBuilder("get")
public class GetBuilderAbstract extends AbstractInstructionBuilder {
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
