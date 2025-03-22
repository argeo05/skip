package cvm;

import cvm.exceptions.EmptyOperandStackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public record Context(
        Stack<StackFrame> stack,
        Map<String, Function> funTable,
        Map<Long, String> constantTable
) {
    public Context() {
        this(new Stack<>(), new HashMap<>(), new HashMap<>());
    }

    public void push(long value) {
        stack.peek().push(value);
    }

    public long pop() {
        return stack.peek().pop();
    }

    public long top() {
        return stack.peek().top();
    }

    public void delFrame() {
        stack.pop();
    }

    public void execFrame() {
        try {
            stack.peek().exec();
            delFrame();
        } catch (EmptyOperandStackException e) {
            var currException = e;

            do {
                delFrame();

                if (!stack.isEmpty()) {
                    currException = new EmptyOperandStackException(currException, stack.peek());
                }
            } while (!stack.isEmpty());

            throw new RuntimeException(currException);
        }
    }

    public void addFun(Function fun) {
        funTable.put(fun.name(), fun);
    }

    public void start() {
        Function main = funTable.get("main");
        stack.add(new StackFrame(main, new long[0]));

        execFrame();
    }

    public void addConst(String constant) {
        constantTable.put((long) constantTable.size(), constant);
    }

    public Function getFunByID(long id) {
        String name = constantTable.get(id);
        return funTable.get(name);
    }

    public long getVar(long ind) {
        return stack.peek().variables()[(int) ind];
    }

    public void setVar(long ind, long value) {
        stack.peek().variables()[(int) ind] = value;
    }

    public void invoke(long id) {
        Function fun = getFunByID(id);
        long[] argv = new long[fun.argc()];

        for (int i = fun.argc() - 1; i >= 0; --i) {
            argv[i] = pop();
        }

        StackFrame frame = new StackFrame(fun, argv);
        stack.push(frame);

        execFrame();
    }

}
