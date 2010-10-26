package pt.inevo.encontra.index;

public class IndexingException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Basic constructor.
     */
    public IndexingException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message Error message.
     */
    public IndexingException(final String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Error message.
     * @param cause Cause of exception.
     */
    public IndexingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     *
     * @param cause Cause of exception.
     */
    public IndexingException(final Throwable cause) {
        super(cause);
    }
}
