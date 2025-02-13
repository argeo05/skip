import cvm.Context;
import cvm.parser.Parser;

public class Main {
    static String code = """
            ; Comment
            fun main
                ld 7
                ld 5
                add
                log""";

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Context ctx = new Context();
        new Parser().setCode(code).setCtx(ctx).parse();

        ctx.start();

    }
}
