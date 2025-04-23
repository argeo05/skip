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

    private int ip = 0;
    private boolean returnFlag = false;
    private long returnValue;

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
     * Sets the instruction pointer to the given instruction index.
     *
     * @param target the index of the instruction to jump to
     */
    public void setIp(int target) {
        this.ip = target;
    }

    /**
     * Indicates whether this frame has performed an explicit return.
     *
     * @return true if a return instruction was executed
     */
    public boolean hasReturned() {
        return returnFlag;
    }

    /**
     * Records the return value and marks this frame as completed.
     *
     * @param value the value to return from this function
     */
    public void returnLong(long value) {
        this.returnValue = value;
        this.returnFlag = true;
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
     * Executes instructions in this frame until a return occurs or the end is reached.
     *
     * @return the value returned by the function
     * @throws RuntimeException if the function ends without a return instruction
     */
    public long exec() {
        while (ip < fun.code().size()) {
            currInstruction = fun.code().get(ip);
            currInstruction.invoke();
            if (returnFlag) {
                return returnValue;
            }
            ip++;
        }
        return 0L;
    }
}
