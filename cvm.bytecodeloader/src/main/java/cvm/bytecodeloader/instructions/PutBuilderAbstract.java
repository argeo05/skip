package cvm.bytecodeloader.instructions;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.special.Put;
import utils.InstrBuilder;

/**
 * <b>PutBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds the put instruction.
 *
 * @link Uses instruction {@link Put}
 * @bytecode put
 */
@InstrBuilder("put")
public class PutBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        long index = Long.parseLong(args[0]);
        return new Put(type, ctx, index);
    }

    @Override
    int getArgsCount() {
        return 1;
    }
}
