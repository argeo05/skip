package cvm;

import cvm.parser.Function;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public record Context(
    Stack<Long> stack,
    Map<String, Function> funTable
) {
    public Context() {
        this(new Stack<>(), new HashMap<>());
    }

    public void push(long value) {
        stack.push(value);
    }

    public long pop() {
        return stack.pop();
    }

    public long top() {
        return stack.peek();
    }

    public void addFun(Function fun) {
        funTable.put(fun.name(), fun);
    }

    public void start() {
        funTable.get("main").exec();
    }
}
