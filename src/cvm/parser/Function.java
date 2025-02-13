package cvm.parser;

import cvm.instructions.VMInstruction;

import java.util.ArrayList;
import java.util.List;

public record Function(
        String name,
        List<VMInstruction> code
) {
    public Function(String name) {
        this(name, new ArrayList<>()); // TODO maybe LinkedList
    }

    public void exec() {
        code.forEach(VMInstruction::exec);
    }

    public void addInstruction(VMInstruction instr) {
        code.add(instr);
    }
}
