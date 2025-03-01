module cvm.bytecodeloader {
    requires transitive cvm.core;
    requires utils;

    exports cvm.bytecodeloader;
    exports cvm.bytecodeloader.instructions;

    opens cvm.bytecodeloader.instructions to utils;
}