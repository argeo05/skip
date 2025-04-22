package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Debug</b>
 * Instruction. Pops last stack value and prints it.
 *
 * @bytecode debug
 * @opcode 6
 */
public final class Debug extends AbstractVmInstruction {
    Context ctx;

    public Debug(Byte type, Context ctx) {
        super(type);
        this.ctx = ctx;
    }

    @Override
    public void popArgs() {
    }

    @Override
    public void run() {
        System.out.println(ctx.top());
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
