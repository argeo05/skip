package cvm.instructions.special;

import cvm.Context;
import cvm.instructions.AbstractVmInstruction;

/**
 * <b>Load</b>
 * Instruction. Puts argument into stack.
 *
 * @bytecode ld
 * @opcode 0
 */
public final class Load extends AbstractVmInstruction {
    Context ctx;
    long value;

    public Load(byte type, long value, Context ctx) {
        super(type);
        this.value = value;
        this.ctx = ctx;
    }

    @Override
    public void popArgs() {

    }

    @Override
    public void run() {
        if (this.type == 0) {
            ctx.push(value);
        } else if (this.type == 1) {
            long constIndex = this.value;
            String constStr = ctx.constantTable().get(constIndex);
            if (constStr == null) {
                throw new RuntimeException("Constant not found by index: " + constIndex);
            }
            long value;
            try {
                if (constStr.contains(".")) {
                    float f = Float.parseFloat(constStr);
                    value = Float.floatToIntBits(f);
                } else {
                    value = Long.parseLong(constStr);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Unknown number format " + constIndex + ": " + constStr);
            }
            ctx.push(value);
        }
    }

    @Override
    public int getArgsCount() {
        return 1;
    }
}
