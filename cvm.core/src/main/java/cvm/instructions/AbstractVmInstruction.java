package cvm.instructions;

import cvm.instructions.logic.Not;
import cvm.instructions.special.Debug;
import cvm.instructions.special.Get;
import cvm.instructions.special.Input;
import cvm.instructions.special.Invoke;
import cvm.instructions.special.Jmp;
import cvm.instructions.special.JmpIf;
import cvm.instructions.special.Load;
import cvm.instructions.special.Log;
import cvm.instructions.special.Put;
import cvm.instructions.special.Return;

/**
 * <b>VMInstruction</b>
 * Based interface for instuctions.
 */
public abstract sealed class AbstractVmInstruction
        permits AbstractBinaryInstructionAbstract, Not, Debug, Get, Input, Invoke, Jmp, JmpIf, Load, Log, Put, Return {
    protected Byte type;

    public AbstractVmInstruction(Byte type) {
        this.type = type;
    }

    protected abstract void popArgs();

    protected abstract void run();

    public abstract int getArgsCount();

    public void invoke() {
        popArgs();
        run();
    }
}
