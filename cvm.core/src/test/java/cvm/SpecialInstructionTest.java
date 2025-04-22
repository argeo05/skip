package cvm;

import static org.junit.jupiter.api.Assertions.*;

import cvm.instructions.special.Get;
import cvm.instructions.special.Ld;
import cvm.instructions.special.Put;
import cvm.instructions.special.Invoke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for special CVM instructions: Ld, Get, Put, and Invoke.
 */
class SpecialInstructionTest {
    private Context ctx;

    /**
     * Sets up a fresh Context and initializes the operand stack with a base frame
     * before each test.
     */
    @BeforeEach
    void setUp() {
        ctx = new Context();
        // Push initial frame to allow stack operations
        ctx.getStack().push(new StackFrame(new Function("f", 0, 1), new long[0], 10));
    }

    /**
     * Tests that loading an immediate value pushes the correct long onto the operand stack.
     */
    @Test
    void loadImmediate() {
        new Ld((byte) 0, 42L, ctx).invoke();
        assertEquals(42L, ctx.pop(), "Immediate load should push the provided value onto the stack");
    }

    /**
     * Tests that loading a constant by index retrieves the correct value from the constant table.
     */
    @Test
    void loadConstant() {
        ctx.addConst("123");
        new Ld((byte) 1, 0L, ctx).invoke();
        assertEquals(123L, ctx.pop(), "Constant load should push the parsed constant value onto the stack");
    }

    /**
     * Tests storing a variable at a given index and retrieving it immediately.
     */
    @Test
    void putAndGetVariable() {
        ctx.push(77L);
        new Put((byte) 0, ctx, 0L).invoke();
        new Get((byte) 0, ctx, 0L).invoke();
        assertEquals(77L, ctx.pop(), "Put followed by Get should retrieve the original value");
    }

    /**
     * Tests invoking a simple function that pushes a known value onto the stack.
     */
    @Test
    void invokeSimpleFunction() {
        ctx.addConst("foo");
        Function foo = new Function("foo", 0, 0);
        foo.addInstruction(new Ld((byte) 0, 7L, ctx));
        ctx.addFun(foo);

        int initialStackSize = ctx.getStack().size();
        new Invoke((byte) 0, 0L, ctx).invoke();
        assertEquals(initialStackSize, ctx.getStack().size(),
                "After invocation, the context should unwind back to the caller frame");
    }
}