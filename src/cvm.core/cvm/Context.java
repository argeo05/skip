package cvm;

import cvm.exceptions.EmptyOperandStackException;
import cvm.exceptions.DivisionByZeroException;
import cvm.exceptions.StackOverflowException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Represents the execution context of the virtual machine.
 */
public class Context {
    private final Stack<StackFrame> stack;
    private final Map<String, Function> funTable;
    private final Map<Long, String> constantTable;
    private int operandStackLimit = 1024;

    /**
     * Default constructor.
     */
    public Context() {
        this(new Stack<>(), new HashMap<>(), new HashMap<>());
    }

    /**
     * Constructs a Context with the specified stack, function table, and constant table.
     *
     * @param stack the stack of StackFrame objects
     * @param funTable the function table mapping function names to Function objects
     * @param constantTable the constant table mapping constant IDs to string values
     */
    public Context(Stack<StackFrame> stack, Map<String, Function> funTable, Map<Long, String> constantTable) {
        this.stack = stack;
        this.funTable = funTable;
        this.constantTable = constantTable;
    }

    /**
     * Returns the stack.
     *
     * @return the stack of StackFrame objects
     */
    public Stack<StackFrame> getStack() {
        return stack;
    }

    /**
     * Returns the function table.
     *
     * @return the function table
     */
    public Map<String, Function> getFunTable() {
        return funTable;
    }

    /**
     * Returns the constant table.
     *
     * @return the constant table
     */
    public Map<Long, String> constantTable() {
        return constantTable;
    }

    /**
     * Sets the operand stack limit in number of long values.
     *
     * @param limit the maximum number of long values allowed in the operand stack
     */
    public void setOperandStackLimit(int limit) {
        this.operandStackLimit = limit;
    }

    /**
     * Sets the operand stack limit in bytes.
     *
     * @param bytes the maximum size of the operand stack in bytes
     */
    public void setOperandStackLimitInBytes(long bytes) {
        this.operandStackLimit = (int) (bytes / 8);
    }

    /**
     * Pushes a value onto the current stack frame's operand stack.
     *
     * @param value the value to push
     */
    public void push(long value) {
        stack.peek().push(value);
    }

    /**
     * Pops a value from the current stack frame's operand stack.
     *
     * @return the popped value
     */
    public long pop() {
        return stack.peek().pop();
    }

    /**
     * Returns the top value of the current stack frame's operand stack.
     *
     * @return the top value
     */
    public long top() {
        return stack.peek().top();
    }

    /**
     * Deletes the current stack frame.
     */
    public void delFrame() {
        stack.pop();
    }

    /**
     * Executes the current stack frame and unwinds the stack if an exception occurs.
     */
    public void execFrame() {
        try {
            stack.peek().exec();
            delFrame();
        } catch (EmptyOperandStackException | DivisionByZeroException | StackOverflowException e) {
            unwindStack();
            throw new RuntimeException("Execution terminated: " + e.getMessage(), e);
        }
    }

    /**
     * Unwinds the entire stack by deleting all stack frames.
     */
    private void unwindStack() {
        while (!stack.isEmpty()) {
            delFrame();
        }
    }

    /**
     * Adds a function to the function table.
     *
     * @param fun the function to add
     */
    public void addFun(Function fun) {
        funTable.put(fun.name(), fun);
    }

    /**
     * Starts execution by invoking the 'main' function.
     */
    public void start() {
        Function main = funTable.get("main");
        stack.add(new StackFrame(main, new long[0], operandStackLimit));
        execFrame();
    }

    /**
     * Adds a constant to the constant table.
     *
     * @param constant the constant value as a string
     */
    public void addConst(String constant) {
        constantTable.put((long) constantTable.size(), constant);
    }

    /**
     * Retrieves a function by its constant ID.
     *
     * @param id the constant ID
     * @return the corresponding function
     */
    public Function getFunByID(long id) {
        String name = constantTable.get(id);
        return funTable.get(name);
    }

    /**
     * Retrieves a variable from the current stack frame.
     *
     * @param ind the variable index
     * @return the variable value
     */
    public long getVar(long ind) {
        return stack.peek().variables()[(int) ind];
    }

    /**
     * Sets a variable in the current stack frame.
     *
     * @param ind the variable index
     * @param value the value to set
     */
    public void setVar(long ind, long value) {
        stack.peek().variables()[(int) ind] = value;
    }

    /**
     * Invokes a function by its constant ID.
     *
     * @param id the constant ID corresponding to the function
     */
    public void invoke(long id) {
        Function fun = getFunByID(id);
        long[] argv = new long[fun.argc()];
        for (int i = fun.argc() - 1; i >= 0; --i) {
            argv[i] = pop();
        }
        StackFrame frame = new StackFrame(fun, argv, operandStackLimit);
        for (long arg : argv) {
            frame.push(arg);
        }
        stack.push(frame);
        execFrame();
    }
}
