package cvm.exceptions;

public class DivisionByZeroException extends VmRuntimeException {
    public DivisionByZeroException() {
        super("Division by zero encountered");
    }
}
