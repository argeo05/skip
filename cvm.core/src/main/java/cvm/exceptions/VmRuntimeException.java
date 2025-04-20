package cvm.exceptions;

public class VmRuntimeException extends RuntimeException {
    public VmRuntimeException(String message) {
        super(message);
    }

    public VmRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
