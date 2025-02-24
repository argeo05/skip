package app;

import cvm.Context;
import cvm.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    static String codeDebug = """
            ; Comment
            fun main
                ld 7
                ld 5
                add
                log""";

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                startVM(codeDebug);
            } else {
                String fileContent = Files.readString(Paths.get(args[0]));
                startVM(fileContent);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
    }

    private static void startVM(String code) {
        System.out.println("Starting the virtual machine...");
        Context ctx = new Context();
        new Parser().setCode(code).setCtx(ctx).parse();
        ctx.start();
    }
}