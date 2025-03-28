package app;

import cvm.Context;
import cvm.ContextBuilder;
import cvm.bytecodeloader.ByteCodeLoader;

/**
 * Entry point of the application.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args command-line arguments; the first argument is the path to the binary file,
     *             the optional second argument is the operand stack limit.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("0 arguments given, must be at least 1");
        }
        if (args.length > 1) {
            startVM(args[0], args[1]);
        } else {
            startVM(args[0], null);
        }
    }

    /**
     * Starts the virtual machine with the provided input file path and optional operand stack limit.
     *
     * @param path the path to the binary file
     * @param stackLimitArg the operand stack limit argument
     */
    private static void startVM(String path, String stackLimitArg) {
        Context ctx = new ContextBuilder().build();
        if (stackLimitArg != null) {
            int limit = parseStackLimit(stackLimitArg);
            ctx.setOperandStackLimit(limit);
        }
        new ByteCodeLoader().setInputPath(path).setCtx(ctx).parse();
        ctx.start();
    }

    /**
     * Parses the operand stack limit argument.
     *
     * @param arg the operand stack limit as a string (e.g. "8192b", "8kb", "1mb", "1gb")
     * @return the operand stack limit in number of long values
     */
    private static int parseStackLimit(String arg) {
        arg = arg.toLowerCase().trim();
        long bytes;
        if (arg.endsWith("gb")) {
            bytes = Long.parseLong(arg.substring(0, arg.length() - 2).trim()) * 1024L * 1024 * 1024;
        } else if (arg.endsWith("mb")) {
            bytes = Long.parseLong(arg.substring(0, arg.length() - 2).trim()) * 1024L * 1024;
        } else if (arg.endsWith("kb")) {
            bytes = Long.parseLong(arg.substring(0, arg.length() - 2).trim()) * 1024L;
        } else if (arg.endsWith("b")) {
            bytes = Long.parseLong(arg.substring(0, arg.length() - 1).trim());
        } else {
            bytes = Long.parseLong(arg);
        }
        return (int) (bytes / 8);
    }
}
