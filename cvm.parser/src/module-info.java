module cvm.parser {
    requires transitive cvm.core;
    requires utils;

    exports cvm.parser;
    exports cvm.parser.instructions;

    opens cvm.parser.instructions to utils;
}