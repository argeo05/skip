package cvm;

import java.util.Stack;
import cvm.instructions.VMInstruction;
import cvm.exceptions.EmptyOperandStackException;

public class StackFrame {
    VMInstruction currInstruction;
    Stack<Long> stack;
    cvm.Function fun;
    long[] variables;

    private StackFrame(Stack<Long> stack, Function fun, long[] variables) {
        this.stack = stack;
        this.fun = fun;
        this.variables = variables;
    }

    public VMInstruction currInstruction() {
        return currInstruction;
    }

    public Stack<Long> stack() {
        return stack;
    }

    public Function fun() {
        return fun;
    }

    public long[] variables() {
        return variables;
    }

    public StackFrame(Function fun, long[] initialValues) {
        this(new Stack<>(), fun, new long[fun.variablesCount()]);
        System.arraycopy(
                initialValues,
                0,
                variables,
                0,
                initialValues.length
        );
    }

    public void push(long value) {
        stack.push(value);
    }

    public long pop() {
        if (stack.isEmpty()) {
            throw new EmptyOperandStackException(this);
        }

        return stack.pop();
    }

    public long top() {
        return stack.peek();
    }

    public void exec() {
        for (VMInstruction instruction : fun.code()) {
            currInstruction = instruction;
            instruction.invoke();
        }
    }
}
