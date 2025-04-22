package cvm.instructions.special;

import java.util.Scanner;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Input</b> Instruction. Reads an integer from standard input and pushes it onto the stack.
 *
 * @bytecode Input
 * @opcode 10
 */
public final class Input extends AbstractVmInstruction {
    private static final Scanner SCANNER = new Scanner(System.in);
    private Context ctx;

    public Input(Byte type, Context ctx) {
        super(type);
        this.ctx = ctx;
    }

    @Override
    protected void popArgs() { }

    @Override
    protected void run() {
        long value = SCANNER.nextLong();
        ctx.push(value);
    }

    @Override
    public int getArgsCount() {
        return 0;
    }
}
