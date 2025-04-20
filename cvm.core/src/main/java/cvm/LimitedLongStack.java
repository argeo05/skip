package cvm;

import java.util.Stack;
import cvm.exceptions.StackOverflowException;

/**
 * A stack of long values with a fixed maximum capacity.
 */
public class LimitedLongStack {
    private final Stack<Long> stack = new Stack<>();
    private final int maxSize;

    /**
     * Creates a new LimitedLongStack that can hold up to {@code maxSize} values.
     *
     * @param maxSize the maximum number of elements permitted on the stack
     */
    public LimitedLongStack(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Pushes a value onto the top of the stack.
     *
     * @param value the long value to push
     * @throws StackOverflowException if the stack has reached its maximum size
     */
    public void push(long value) {
        if (stack.size() >= maxSize) {
            throw new StackOverflowException(
                "Operand stack overflow: maximum size " + maxSize + " reached."
            );
        }
        stack.push(value);
    }

    /**
     * Pops and returns the value at the top of the stack.
     *
     * @return the popped long value
     * @throws RuntimeException if the stack is empty
     */
    public long pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Operand stack underflow.");
        }
        return stack.pop();
    }

    /**
     * Peeks at the value at the top of the stack without removing it.
     *
     * @return the top long value
     * @throws RuntimeException if the stack is empty
     */
    public long peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Operand stack is empty.");
        }
        return stack.peek();
    }

    /**
     * Returns the current number of elements in the stack.
     *
     * @return the stack size
     */
    public int size() {
        return stack.size();
    }

    /**
     * Checks whether the stack is empty.
     *
     * @return {@code true} if the stack has no elements, {@code false} otherwise
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
