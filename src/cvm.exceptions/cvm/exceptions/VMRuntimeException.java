package cvm.exceptions;

public class VMRuntimeException extends RuntimeException {
    public VMRuntimeException(String message) {
        super(message);
    }

    public VMRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
