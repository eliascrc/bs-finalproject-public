package cr.brainstation.bsfinalproject.utils.exceptions;

public class NonExistingBookException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public NonExistingBookException() {
        super("The queried book does not exist in the repository");
    }

    /**
     * Creates a new exception with the specified message
     * @param message the message to display
     */
    public NonExistingBookException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the specified wrapped exception
     * @param cause the cause of the exception
     */
    public NonExistingBookException(Throwable cause) {
        super("Book's name cannot be null", cause);
    }

    /**
     * Creates a new exception with the specified message and wrapped exception
     * @param message the message to display
     * @param cause the cause of the exception
     */
    public NonExistingBookException(String message, Throwable cause) {
        super(message, cause);
    }

}
