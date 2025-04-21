package cvm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import cvm.exceptions.StackOverflowException;

class LimitedLongStackTest {

    @Test
    void pushAndPop() {
        LimitedLongStack stack = new LimitedLongStack(2);
        stack.push(10L);
        stack.push(20L);
        assertEquals(20L, stack.pop());
        assertEquals(10L, stack.pop());
    }

    @Test
    void overflowThrows() {
        LimitedLongStack stack = new LimitedLongStack(1);
        stack.push(5L);
        assertThrows(
                StackOverflowException.class,
                () -> stack.push(6L)
        );
    }

    @Test
    void underflowThrows() {
        LimitedLongStack stack = new LimitedLongStack(1);
        assertThrows(
                RuntimeException.class,
                stack::pop
        );
    }
}
