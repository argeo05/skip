package cvm.exceptions;

public class StackOverflowException extends VmRuntimeException {
    public StackOverflowException(String message) {
        super(message);
    }
}
