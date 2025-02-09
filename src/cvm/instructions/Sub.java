package cvm.instructions;

import cvm.Context;

/**
 * <b>Sub</b>
 * Instruction that gives a subtraction.
 *
 * @bytecode sub
 * @stackArg lhs minuend
 * @stackArg rhs subtrahend
 */

public final class Sub extends BinaryInstruction {
  public Sub(Context ctx) {
    super(ctx);
  }

  @Override
  long calc() {
    return lhs - rhs;
  }
}
