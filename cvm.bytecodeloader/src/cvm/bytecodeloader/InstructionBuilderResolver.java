package cvm.bytecodeloader;

import utils.ClassFinder;
import utils.InstrBuilder;
import cvm.bytecodeloader.instructions.InstructionBuilder;

import java.util.List;

public class InstructionBuilderResolver {
    private static final List<Class<?>> CLASSES = new ClassFinder("cvm.bytecodeloader.instructions").findClasses();

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