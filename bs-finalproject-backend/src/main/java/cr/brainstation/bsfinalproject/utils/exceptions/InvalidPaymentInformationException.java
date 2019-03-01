package cr.brainstation.bsfinalproject.utils.exceptions;

public class InvalidPaymentInformationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public InvalidPaymentInformationException() {
        super();
    }

    /**
     * Creates a new exception with the specified message
     * @param message the message to display
     */
    public InvalidPaymentInformationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the specified wrapped exception
     * @param cause the cause of the exception
     */
    public InvalidPaymentInformationException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception with the specified message and wrapped exception
     * @param message the message to display
     * @param cause the cause of the exception
     */
    public InvalidPaymentInformationException(String message, Throwable cause) {
        super(message, cause);
    }

}
