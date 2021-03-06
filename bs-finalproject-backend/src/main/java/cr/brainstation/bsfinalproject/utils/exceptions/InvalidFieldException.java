package cr.brainstation.bsfinalproject.utils.exceptions;

public class InvalidFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public InvalidFieldException() {
        super();
    }

    /**
     * Creates a new exception with the specified message
     * @param message the message to display
     */
    public InvalidFieldException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the specified wrapped exception
     * @param cause the cause of the exception
     */
    public InvalidFieldException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception with the specified message and wrapped exception
     * @param message the message to display
     * @param cause the cause of the exception
     */
    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }

}