package cvm.parser;

import cvm.parser.instructions.AddBuilder;
import cvm.parser.instructions.InstructionBuilder;
import cvm.parser.instructions.LoadBuilder;
import cvm.parser.instructions.LogBuilder;

public final class InstructionBuilderResolver {
    public static InstructionBuilder resolve(String instrName) {
        return switch (instrName) {
            case "ld" -> new LoadBuilder();
            case "log" -> new LogBuilder();
            case "add" -> new AddBuilder();
            default -> throw new IllegalArgumentException(
                    "Instruction was not found: " + instrName);
        };
    }
}
