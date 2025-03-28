package cvm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ContextBuilder {
    private Stack<StackFrame> stack = new Stack<>();
    private Map<String, Function> funTable = new HashMap<>();
    private Map<Long, String> constantTable = new HashMap<>();

    public ContextBuilder withStack(Stack<StackFrame> stack) {
        this.stack = stack;
        return this;
    }

    public ContextBuilder withFunTable(Map<String, Function> funTable) {
        this.funTable = funTable;
        return this;
    }

    public ContextBuilder withConstantTable(Map<Long, String> constantTable) {
        this.constantTable = constantTable;
        return this;
    }

    public Context build() {
        return new Context(stack, funTable, constantTable);
    }
}
