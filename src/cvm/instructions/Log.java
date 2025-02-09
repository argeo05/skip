package cvm.instructions;

import cvm.Context;

/**
 * <b>Log</b>
 * Instruction that prints to log.
 *
 * @bytecode log
 * @stackArg value argument
 */

public final class Log implements VMInstruction {
  Context ctx;
  long value;

  public Log(Context ctx) {
    this.ctx = ctx;
  }

  @Override
  public void popArgs() {
    value = ctx.pop();
  }

  @Override
  public void exec() {
    System.out.println(value);
  }
}
