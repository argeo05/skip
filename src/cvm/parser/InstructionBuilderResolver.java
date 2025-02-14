package cvm.parser;

import utils.InstrBuilder;
import cvm.parser.instructions.InstructionBuilder;

import java.util.List;

public class InstructionBuilderResolver {
    private static final List<Class<?>> CLASSES = new ClassFinder("cvm.parser.instructions").findClasses();

    public static InstructionBuilder resolve(String instrName) throws Exception {
        for (Class<?> clazz : CLASSES) {
            InstrBuilder annotation = clazz.getAnnotation(InstrBuilder.class);
            if (annotation != null && annotation.value().equals(instrName)) {
                return (InstructionBuilder) clazz.getDeclaredConstructor().newInstance();
            }
        }

        throw new IllegalArgumentException("Builder not found for: " + instrName);
    }
}