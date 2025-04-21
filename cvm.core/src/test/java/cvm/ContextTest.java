package cvm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Context}, verifying operand stack limits,
 * frame operations, error handling, and builder behavior.
 */
class ContextTest {
    private Context ctx;

    /**
     * Initializes a fresh Context before each test.
     */
    @BeforeEach
    void setUp() {
        ctx = new Context();
    }

    /**
     * Tests that setting the operand stack limit (in number of values and in bytes)
     * correctly triggers a RuntimeException when the limit is exceeded.
     */
    @Test
    void testSetOperandStackLimitAndBytes() {
        ctx.setOperandStackLimit(2);
        ctx.addConst("f");
        Function f = new Function("f", 0, 0);
        ctx.addFun(f);
        assertThrows(RuntimeException.class, () -> {
            ctx.invoke(0L);
            ctx.push(1);
            ctx.push(2);
            ctx.push(3);
        });

        ctx = new Context();
        ctx.setOperandStackLimitInBytes(16);
        ctx.addConst("f");
        ctx.addFun(f);
        assertThrows(RuntimeException.class, () -> {
            ctx.invoke(0L);
            ctx.push(1);
            ctx.push(2);
            ctx.push(3);
        });
    }

    /**
     * Tests the behavior of {@link Context#start()}, {@link Context#top()}, and {@link Context#delFrame()}.
     * Verifies that start resets the stack and top/delFrame operate correctly on a frame.
     */
    @Test
    void testTopAndDelFrame() {
        Function main = new Function("main", 0, 0);
        ctx.addConst("main");
        ctx.addFun(main);
        ctx.start();
        assertTrue(ctx.getStack().isEmpty());

        ctx.getStack().push(new StackFrame(main, new long[0], 10));
        ctx.push(42);
        assertEquals(42, ctx.top());
        ctx.delFrame();
        assertTrue(ctx.getStack().isEmpty());
    }

    /**
     * Tests that execution of a frame that throws an exception
     * correctly unwinds the stack and wraps the error in a RuntimeException.
     */
    @Test
    void testExecFrameUnwindsStackOnError() {
        Function bad = new Function("main", 0, 0);
        bad.addInstruction(new cvm.instructions.arithmetic.Div((byte) 0, ctx));
        ctx.addConst("main");
        ctx.addFun(bad);
        RuntimeException ex = assertThrows(RuntimeException.class, ctx::start);
        assertTrue(ex.getMessage().contains("Execution terminated"));
    }

    /**
     * Tests that {@link ContextBuilder} correctly sets custom stack,
     * function table, and constant table on the built Context.
     */
    @Test
    void builderSetsTables() {
        var stack = new java.util.Stack<StackFrame>();
        var funs = new java.util.HashMap<String, Function>();
        var consts = new java.util.HashMap<Long, String>();
        Context ctx = new ContextBuilder()
                .withStack(stack)
                .withFunTable(funs)
                .withConstantTable(consts)
                .build();
        assertSame(stack, ctx.getStack());
        assertSame(funs, ctx.getFunTable());
        assertSame(consts, ctx.constantTable());
    }

    /**
     * Tests that adding a constant and retrieving a function by its constant ID works correctly.
     */
    @Test
    void addConstAndGetFunByID() {
        Context ctx = new ContextBuilder().build();
        ctx.addConst("main");
        Function f = new Function("main", 0, 0);
        ctx.addFun(f);
        assertEquals(f, ctx.getFunByID(0L));
    }
}
