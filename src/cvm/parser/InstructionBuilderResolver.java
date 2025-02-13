package cvm.parser;

import cvm.parser.instructions.InstructionBuilder;

public class InstructionBuilderResolver {
    private static final ClassFinder FINDER = new ClassFinder("cvm.parser.instructions");

    public static InstructionBuilder resolve(String name) throws Exception {
        return FINDER.resolve(name);
    }
}