package cvm.instructions;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerates the available commands with their corresponding opcodes.
 */
public enum Commands {
    ID(0),
    ADD(1),
    SUB(2),
    MUL(3),
    DIV(4),
    LOG(5),
    DEBUG(6);

    private final int opcode;
    private static final Map<Integer, Commands> opcodeToCommandMap = new HashMap<>();

    static {
        for (Commands command : Commands.values()) {
            opcodeToCommandMap.put(command.opcode, command);
        }
    }

    Commands(int opcode) {
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
    public static Commands fromOpcode(int opcode) {
        return opcodeToCommandMap.get(opcode);
    }
}