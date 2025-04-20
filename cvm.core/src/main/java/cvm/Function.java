package cvm;

import java.util.LinkedList;
import java.util.List;

import cvm.instructions.AbstractVmInstruction;

/**
 * Represents a virtual machine function, including its name,
 * argument count, variable count, and instruction code.
 *
 * @param name            the unique name of the function
 * @param argc            the number of arguments this function accepts
 * @param variablesCount  the number of local variables
 * @param code            the list of {@link AbstractVmInstruction} instances that make up the function body
 */
public record Function(
    String name,
    int argc,
    int variablesCount,
    List<AbstractVmInstruction> code
) {
    /**
     * Constructs a Function with an empty instruction list.
     *
     * @param name           the unique name of the function
     * @param argc           the number of arguments this function accepts
     * @param variablesCount the number of local variables
     */
    public Function(String name, int argc, int variablesCount) {
        this(name, argc, variablesCount, new LinkedList<>());
    }

    /**
     * Adds an instruction to the end of this function's instruction list.
     *
     * @param instr the instruction to add
     */
    public void addInstruction(AbstractVmInstruction instr) {
        code.add(instr);
    }
}
