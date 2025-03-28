package cvm;

import java.util.Stack;
import cvm.exceptions.StackOverflowException;

public class LimitedLongStack {
    private final Stack<Long> stack = new Stack<>();
    private final int maxSize;

    public LimitedLongStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(long value) {
        if (stack.size() >= maxSize) {
            throw new StackOverflowException("Operand stack overflow: maximum size " + maxSize + " reached.");
        }
        stack.push(value);
    }

    public long pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Operand stack underflow.");
        }
        return stack.pop();
    }

    public long peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Operand stack is empty.");
        }
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
