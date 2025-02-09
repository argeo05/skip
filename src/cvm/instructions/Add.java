package cvm.instructions;

import cvm.Context;

/**
 * <b>Add</b>
 * instruction. Pops two arguments and pushes their sum.
 *
 * @bytecode add
 * @stackArg lhs first summand
 * @stackArg rhs second summand
 */

public final class Add extends BinaryInstruction {
  public Add(Context ctx) {
    super(ctx);
  }

  @Override
  long calc() {
    return lhs + rhs;
  }
}
