package cvm;

import static org.junit.jupiter.api.Assertions.*;

import cvm.instructions.Instructions;
import org.junit.jupiter.api.Test;

class InstructionsEnumTest {

    @Test
    void opcodeMapping() {
        assertEquals(0, Instructions.LD.getOpcode());
        assertEquals(Instructions.ADD, Instructions.fromOpcode(1));
        assertEquals(Instructions.DIV, Instructions.fromOpcode(4));
        assertNull(Instructions.fromOpcode(99));
    }
}
