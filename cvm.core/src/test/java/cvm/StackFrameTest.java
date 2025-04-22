package cvm;

import static org.junit.jupiter.api.Assertions.*;

import cvm.instructions.special.Ld;
import cvm.exceptions.EmptyOperandStackException;
import org.junit.jupiter.api.Test;

class StackFrameTest {

    @Test
    void popEmptyThrows() {
        Function f = new Function("f", 0, 0);
        StackFrame frame = new StackFrame(f, new long[0], 5);
        assertThrows(
                EmptyOperandStackException.class,
                frame::pop
        );
    }

    @Test
    void execSimpleFunction() {
        Function f = new Function("f", 0, 0);
        Context ctx = new Context();
        StackFrame frame = new StackFrame(f, new long[0], 5);
        ctx.getStack().push(frame);
        f.addInstruction(new Ld((byte)0, 9L, ctx));
        frame.exec();
        assertEquals(9L, frame.top());
    }
}