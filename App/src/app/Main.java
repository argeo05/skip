package app;

import cvm.Context;
import cvm.bytecodeloader.ByteCodeLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                startVM("cvm.bytecodeloader/resources/input.txt");
            } else {
                String fileContent = Files.readString(Paths.get(args[0]));
                startVM(fileContent);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
    }

    private static void startVM(String path) {
        Context ctx = new Context();
        new ByteCodeLoader().setInputPath(path).setCtx(ctx).parse();
        ctx.start();
    }
}