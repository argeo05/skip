package cvm.instructions;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerates the available commands with their corresponding opcodes.
 */
public enum Instructions {
    LD(0),
    ADD(1),
    SUB(2),
    MUL(3),
    DIV(4),
    LOG(5),
    DEBUG(6),
    GET(7),
    PUT(8),
    INVOKE(9),
    INPUT(10),
    MOD(11),
    COMPARE(12),
    EQUAL(13),
    AND(14),
    OR(15),
    NOT(16),
    XOR(17),
    JMP(18),
    JMPIF(19),
    RETURN(20);

    private static final Map<Integer, Instructions> OPCODE_TO_COMMAND_MAP = new HashMap<>();
    private final int opcode;

    static {
        for (Instructions command : Instructions.values()) {
            OPCODE_TO_COMMAND_MAP.put(command.opcode, command);
        }
    }

    Instructions(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }

    /**
     * Returns the enum constant with the specified opcode.
     *
     * @param opcode The opcode to look up.
     * @return The enum constant with the specified opcode, or null if no such constant exists.
     */
    public static Instructions fromOpcode(int opcode) {
        return OPCODE_TO_COMMAND_MAP.get(opcode);
    }
}
