package cvm.bytecodeloader;

import utils.ClassFinder;
import utils.InstrBuilder;
import cvm.bytecodeloader.instructions.InstructionBuilder;

import java.util.List;

/**
 * Factory‑style utility that translates a textual mnemonic into an
 * {@link InstructionBuilder} instance.
 */
public class InstructionBuilderResolver {

    /**
     * Eagerly discovered list of builder implementation classes.
     */
    private static final List<Class<?>> CLASSES = new ClassFinder("cvm.bytecodeloader.instructions").findClasses();

    /**
     * Looks up and instantiates a concrete {@link InstructionBuilder} capable
     * of constructing the instruction denoted by {@code instrName}.
     *
     * @param instrName lower‑case mnemonic such as {@code "add"} or
     *                  {@code "invoke"}
     * @return a fresh {@link InstructionBuilder}
     * @throws IllegalArgumentException if no builder with the supplied mnemonic
     *                                  is found
     * @throws Exception                if the builder class could not be
     *                                  instantiated (e.g. missing no‑arg
     *                                  constructor)
     */
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
