package cvm;

import cvm.instructions.VMInstruction;

import java.util.LinkedList;
import java.util.List;

public record Function(
        String name,
        int argc,
        int variablesCount,
        List<VMInstruction> code
) {
    public Function(String name, int argc, int variablesCount) {
        this(name, argc, variablesCount, new LinkedList<>());
    }

    public void addInstruction(VMInstruction instr) {
        code.add(instr);
    }
}
