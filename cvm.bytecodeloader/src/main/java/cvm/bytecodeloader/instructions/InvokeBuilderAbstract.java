package cvm.bytecodeloader.instructions;

import java.util.Arrays;

import cvm.instructions.AbstractVmInstruction;
import cvm.instructions.special.Invoke;
import utils.InstrBuilder;



/**
 * <b>InvokeBuilderAbstract</b>
 * AbstractInstructionBuilder. Builds the invoke instruction.
 *
 * @link Uses instruction {@link Invoke}
 * @bytecode invoke
 */
@InstrBuilder("invoke")
public class InvokeBuilderAbstract extends AbstractInstructionBuilder {
    @Override
    AbstractVmInstruction construct(byte type) {
        if (args[0].charAt(0) != '#') {
            throw new IllegalArgumentException("Wrong constant id: " + Arrays.toString(args));
        }

        long id = Long.parseLong(args[0].substring(1));
        return new Invoke(type, id, ctx);
    }

    @Override
    protected int getArgsCount() {
        return 1;
    }
}
