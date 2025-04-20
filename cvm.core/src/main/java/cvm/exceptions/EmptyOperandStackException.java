package cvm.exceptions;

import cvm.StackFrame;

public class EmptyOperandStackException extends RuntimeException {
    public EmptyOperandStackException(StackFrame frame) {
        super("""
                OperandStack: empty stack!!!
                at %s.%s""".formatted(frame.fun().name(), frame.currInstruction()));
    }

    public EmptyOperandStackException(EmptyOperandStackException e, StackFrame frame) {
        super(
            """
            %s
            at %s.%s""".formatted(
                    e.getMessage(),
                    frame.fun().name(),
                    frame.currInstruction()
            )
        );
    }
}
