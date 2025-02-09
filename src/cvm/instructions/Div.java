package cvm.instructions;

import cvm.Context;

/**
 * <b>Div</b>
 * Instruction that gives a division.
 *
 * @bytecode div
 * @stackArg lhs dividend
 * @stackArg rhs divider
 */

public final class Div extends BinaryInstruction {
  public Div(Context ctx) {
    super(ctx);
  }

  @Override
  long calc() {
    return lhs / rhs;
  }
}
