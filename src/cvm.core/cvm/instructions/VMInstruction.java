package cvm.instructions;

/**
 * <b>VMInstruction</b>
 * Based interface for instuctions.
 */
public sealed abstract class VMInstruction permits BinaryInstruction, Debug, Load, Log {
    private Byte type;

    public VMInstruction(Byte type) {
        this.type = type;
    }

    abstract void popArgs();

    abstract void run();

    public void exec() {
        popArgs();
        run();
    }
}
