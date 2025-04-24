package cvm.bytecodeloader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;
import utils.ClassFinder;

public class ReflectiveInstructionFactory {

    private static final List<Class<?>> INSTRUCTION_CLASSES =
            new ClassFinder("cvm.instructions").findClasses();

    /**
     * Creates an instruction instance based on its mnemonic, arguments, context, and type.
     *
     * @param mnemonic lower-case instruction name
     * @param args     instruction arguments
     * @param ctx      execution context
     * @param type     instruction type byte
     * @return a new AbstractVmInstruction
     */
    public static AbstractVmInstruction create(
            String mnemonic,
            String[] args,
            Context ctx,
            byte type
    ) {
        Class<?> target = findInstructionClass(mnemonic);

        for (Constructor<?> ctor : target.getConstructors()) {
            Class<?>[] pts = ctor.getParameterTypes();

            if (pts.length != args.length + 2) {
                continue;
            }

            Object[] initArgs = new Object[pts.length];
            int argIdx = 0;

            try {
                for (int i = 0; i < pts.length; i++) {
                    Class<?> p = pts[i];
                    if (p == byte.class || p == Byte.class) {
                        initArgs[i] = type;
                    } else if (p == Context.class) {
                        initArgs[i] = ctx;
                    } else if (p == long.class || p == Long.class) {
                        String s = args[argIdx++];
                        if (s.charAt(0) == '#') {
                            s = s.substring(1);
                        }
                        initArgs[i] = Long.parseLong(s);
                    } else if (p == int.class || p == Integer.class) {
                        initArgs[i] = Integer.parseInt(args[argIdx++]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }

                return (AbstractVmInstruction) ctor.newInstance(initArgs);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                     | IllegalArgumentException e) {
                System.out.println(e);
            }
        }

        throw new IllegalArgumentException("Instruction not found for: " + mnemonic);
    }

    private static Class<?> findInstructionClass(String mnemonic) {
        String name = Character.toUpperCase(mnemonic.charAt(0)) + mnemonic.substring(1);
        for (Class<?> cls : INSTRUCTION_CLASSES) {
            if (cls.getSimpleName().equals(name)) {
                return cls;
            }
        }
        throw new IllegalArgumentException("Instruction class not found: " + mnemonic);
    }
}
