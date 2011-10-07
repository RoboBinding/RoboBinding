package robobinding.beans;

/**
 * A runtime exception that describes problems that arise
 * when a bound property cannot be bound properly.
 */
@SuppressWarnings("serial")
public final class PropertyNotBindableException extends PropertyException {
    public PropertyNotBindableException(String message) {
        super(message);
    }


    /**
     * Constructs a new exception instance with the specified detail message
     * and cause.
     *
     * @param message   the detail message which is saved for later retrieval by
     *        the {@link #getMessage()} method.
     * @param cause     the cause which is saved for later retrieval by the
     *        {@link #getCause()} method. A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.
     */
    public PropertyNotBindableException(String message, Throwable cause) {
        super(message, cause);
    }

}
