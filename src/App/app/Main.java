package app;

import cvm.Context;
import cvm.bytecodeloader.ByteCodeLoader;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("0 arguments given, must be 1");
        }

        startVM(args[0]);
    }

    private static void startVM(String path) {
        Context ctx = new Context();
        new ByteCodeLoader().setInputPath(path).setCtx(ctx).parse();
        ctx.start();
    }
}