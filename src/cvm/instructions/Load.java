package cvm.instructions;

import cvm.Context;

/**
 * <b>Load</b>
 * Instruction that takes two arguments to put them into stack.
 *
 * @bytecode load
 */
public final class Load implements VMInstruction {
  Context ctx;
  long value;

  public Load(long value, Context ctx) {
    this.value = value;
    this.ctx = ctx;
  }

  @Override
  public void popArgs() {

  }

  @Override
  public void exec() {
    ctx.push(value);
  }
}
