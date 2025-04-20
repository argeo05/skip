package cvm;

import cvm.exceptions.EmptyOperandStackException;
import cvm.instructions.AbstractVmInstruction;

/**
 * Execution frame for a single function call in the VM.
 *
 * <p>Holds:</p>
 * <ul>
 *   <li>An operand stack with a fixed maximum depth.</li>
 *   <li>The {@link Function} and its instruction list.</li>
 *   <li>Local variables and arguments in a {@code long[]} of length {@code argc + variablesCount}.</li>
 * </ul>
 *
 * <p>Calling {@link #exec()} iterates over each instruction, pops its operands,
 * runs its logic, and pushes any results.</p>
 */
public class StackFrame {
    private AbstractVmInstruction currInstruction;
    private final LimitedLongStack operandStack;
    private final Function fun;
    private final long[] variables;

    private StackFrame(LimitedLongStack operandStack, Function fun, long[] variables) {
        this.operandStack = operandStack;
        this.fun = fun;
        this.variables = variables;
    }

    /**
     * Public constructor, creates a StackFrame with a specified stack limit.
     *
     * @param fun               The function for which the frame is created.
     * @param initialValues     Initial values of the function arguments.
     * @param operandStackLimit Limit on the number of long values in the stack.
     */
    public StackFrame(Function fun, long[] initialValues, int operandStackLimit) {
        this(new LimitedLongStack(operandStackLimit), fun, new long[fun.argc() + fun.variablesCount()]);
        System.arraycopy(initialValues, 0, this.variables, 0, initialValues.length);
    }

    /**
     * Returns the current instruction being executed by the frame.
     *
     * @return the current instruction
     */
    public AbstractVmInstruction currInstruction() {
        return currInstruction;
    }

    /**
     * Returns the function associated with this frame.
     *
     * @return the function
     */
    public Function fun() {
        return fun;
    }

    /**
     * Returns the array of frame variables.
     *
     * @return the array of variables
     */
    public long[] variables() {
        return variables;
    }

    /**
     * Adds a value to the stack.
     *
     * @param value the value to add
     */
    public void push(long value) {
        operandStack.push(value);
    }

    /**
     * Extracts the top value from the stack.
     *
     * @return the top value
     * @throws EmptyOperandStackException if the stack is empty
     */
    public long pop() {
        if (operandStack.isEmpty()) {
            throw new EmptyOperandStackException(this);
        }
        return operandStack.pop();
    }

    /**
     * Returns the top value of the stack without removing it.
     *
     * @return the top value
     */
    public long top() {
        return operandStack.peek();
    }

    /**
     * Executes the sequence of instructions associated with the function.
     */
    public void exec() {
        for (AbstractVmInstruction instruction : fun.code()) {
            currInstruction = instruction;
            instruction.invoke();
        }
    }
}
