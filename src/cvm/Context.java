package cvm;

import java.util.Stack;

public record Context(
    Stack<Long> stack
) {
    public void push(long value) {
        stack.push(value);
    }

    public long pop() {
        return stack.pop();
    }
}
