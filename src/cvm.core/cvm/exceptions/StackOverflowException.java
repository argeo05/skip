package cvm.exceptions;

public class StackOverflowException extends VMRuntimeException {
    public StackOverflowException(String message) {
        super(message);
    }
}
