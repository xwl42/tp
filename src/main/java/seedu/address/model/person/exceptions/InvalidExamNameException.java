package seedu.address.model.person.exceptions;

/**
 * Signals that exam name input by the user is not in the list of valid exam names
 */
public class InvalidExamNameException extends RuntimeException {
    public InvalidExamNameException(String message) {
        super(message);
    }
}
