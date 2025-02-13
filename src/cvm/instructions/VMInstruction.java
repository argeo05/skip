package cvm.instructions;

/**
 * <b>VMInstruction</b>
 * Based interface for instuctions.
 */
public sealed interface VMInstruction permits BinaryInstruction, Debug, Load, Log {
    void popArgs();

    void run();

    default void exec() {
        popArgs();
        run();
    }
}
