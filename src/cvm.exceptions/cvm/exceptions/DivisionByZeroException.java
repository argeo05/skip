package cvm.exceptions;

public class DivisionByZeroException extends VMRuntimeException {
    public DivisionByZeroException() {
        super("Division by zero encountered");
    }
}
