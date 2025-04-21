package cvm;

import static org.junit.jupiter.api.Assertions.*;

import cvm.instructions.arithmetic.Add;
import cvm.instructions.arithmetic.Div;
import cvm.instructions.arithmetic.Mul;
import cvm.instructions.arithmetic.Sub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cvm.exceptions.DivisionByZeroException;

class ArithmeticInstructionTest {
    private Context ctx;

    @BeforeEach
    void setUp() {
        ctx = new Context();
        ctx.getStack().push(new cvm.StackFrame(new cvm.Function("f", 0, 0), new long[0], 10));
    }

    @Test
    void addInstruction() {
        ctx.push(2L);
        ctx.push(3L);
        new Add((byte) 0, ctx).invoke();
        assertEquals(5L, ctx.pop());
    }

    @Test
    void subInstruction() {
        ctx.push(5L);
        ctx.push(3L);
        new Sub((byte) 0, ctx).invoke();
        assertEquals(2L, ctx.pop());
    }

    @Test
    void mulInstruction() {
        ctx.push(4L);
        ctx.push(3L);
        new Mul((byte) 0, ctx).invoke();
        assertEquals(12L, ctx.pop());
    }

    @Test
    void divInstructionNormal() {
        ctx.push(10L);
        ctx.push(2L);
        new Div((byte) 0, ctx).invoke();
        assertEquals(5L, ctx.pop());
    }

    @Test
    void divByZeroThrows() {
        ctx.push(10L);
        ctx.push(0L);
        assertThrows(
                DivisionByZeroException.class,
                () -> new Div((byte) 0, ctx).invoke()
        );
    }
}
