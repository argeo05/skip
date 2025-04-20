package cvm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Builder for {@link Context} instances, allowing customization of
 * the execution context before construction.
 */
public class ContextBuilder {
    private Stack<StackFrame> stack = new Stack<>();
    private Map<String, Function> funTable = new HashMap<>();
    private Map<Long, String> constantTable = new HashMap<>();

    /**
     * Specifies the stack to be used by the Context.
     *
     * @param stack the stack of {@link StackFrame} objects
     * @return this builder instance
     */
    public ContextBuilder withStack(Stack<StackFrame> stack) {
        this.stack = stack;
        return this;
    }

    /**
     * Specifies the function table to be used by the Context.
     *
     * @param funTable mapping from function names to {@link Function} objects
     * @return this builder instance
     */
    public ContextBuilder withFunTable(Map<String, Function> funTable) {
        this.funTable = funTable;
        return this;
    }

    /**
     * Specifies the constant table to be used by the Context.
     *
     * @param constantTable mapping from constant IDs to their string values
     * @return this builder instance
     */
    public ContextBuilder withConstantTable(Map<Long, String> constantTable) {
        this.constantTable = constantTable;
        return this;
    }

    /**
     * Builds a new {@link Context} instance with the configured
     * stack, function table, and constant table.
     *
     * @return a new Context
     */
    public Context build() {
        return new Context(stack, funTable, constantTable);
    }
}