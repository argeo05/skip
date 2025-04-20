package cvm.bytecodeloader.instructions;

import cvm.instructions.special.Put;
import cvm.instructions.AbstractVmInstruction;
import utils.InstrBuilder;

/**
 * <b>PutBuilder</b>
 * InstructionBuilder. Builds the put instruction.
 *
 * @link Uses instruction {@link Put}
 * @bytecode put
 */
@InstrBuilder("put")
public class PutBuilder extends InstructionBuilder {
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
