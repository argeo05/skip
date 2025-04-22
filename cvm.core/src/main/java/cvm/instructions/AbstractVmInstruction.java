package cvm.instructions;

import cvm.instructions.special.Debug;
import cvm.instructions.special.Get;
import cvm.instructions.special.Input;
import cvm.instructions.special.Invoke;
import cvm.instructions.special.Load;
import cvm.instructions.special.Log;
import cvm.instructions.special.Put;

/**
 * <b>VMInstruction</b>
 * Based interface for instuctions.
 */
public abstract sealed class AbstractVmInstruction
        permits AbstractBinaryInstructionAbstract, Debug, Get, Input, Invoke, Load, Log, Put {
    protected Byte type;

    public AbstractVmInstruction(Byte type) {
        this.type = type;
    }

    protected abstract void popArgs();

    protected abstract void run();

    public void invoke() {
        popArgs();
        run();
    }
}
