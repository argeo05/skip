package cvm.bytecodeloader;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cvm.bytecodeloader.instructions.AbstractInstructionBuilder;
import utils.ClassFinder;
import utils.InstrBuilder;

/**
 * Factory‑style utility that translates a textual mnemonic into an
 * {@link AbstractInstructionBuilder} instance.
 */
public class InstructionBuilderResolver {

    /**
     * Eagerly discovered list of builder implementation classes.
     */
    private static final List<Class<?>> CLASSES = new ClassFinder("cvm.bytecodeloader.instructions").findClasses();

    /**
     * Looks up and instantiates a concrete {@link AbstractInstructionBuilder} capable
     * of constructing the instruction denoted by {@code instrName}.
     *
     * @param instrName lower‑case mnemonic such as {@code "add"} or
     *                  {@code "invoke"}
     * @return a fresh {@link AbstractInstructionBuilder}
     * @throws IllegalArgumentException if no builder with the supplied mnemonic
     *                                  is found
     * @throws Exception                if the builder class could not be
     *                                  instantiated (e.g. missing no‑arg
     *                                  constructor)
     */
    public static AbstractInstructionBuilder resolve(String instrName) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        for (Class<?> clazz : CLASSES) {
            InstrBuilder annotation = clazz.getAnnotation(InstrBuilder.class);
            if (annotation != null && annotation.value().equals(instrName)) {
                return (AbstractInstructionBuilder) clazz.getDeclaredConstructor().newInstance();
            }
        }

        throw new IllegalArgumentException("Builder not found for: " + instrName);
    }
}
