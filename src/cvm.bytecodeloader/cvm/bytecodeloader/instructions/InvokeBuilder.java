package cvm.bytecodeloader.instructions;

import cvm.instructions.VMInstruction;
import cvm.instructions.special.Invoke;
import utils.InstrBuilder;

import java.util.Arrays;

/**
 * <b>InvokeBuilder</b>
 * InstructionBuilder. Builds the invoke instruction.
 *
 * @link Uses instruction {@link Invoke}
 * @bytecode invoke
 */
@InstrBuilder("invoke")
public class InvokeBuilder extends InstructionBuilder {
    @Override
    VMInstruction construct(byte type) {
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
