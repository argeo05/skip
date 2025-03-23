package cvm.instructions;

import cvm.instructions.special.Log;
import cvm.instructions.special.*;

/**
 * <b>VMInstruction</b>
 * Based interface for instuctions.
 */
public sealed abstract class VMInstruction permits BinaryInstruction, Debug, Get, Invoke, Load, Log, Put {
    protected Byte type;

    public VMInstruction(Byte type) {
        this.type = type;
    }

    protected abstract void popArgs();

    protected abstract void run();

    public void invoke() {
        popArgs();
        run();
    }
}
