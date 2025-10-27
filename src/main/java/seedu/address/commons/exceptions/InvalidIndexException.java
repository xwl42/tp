package seedu.address.commons.exceptions;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Thrown when the index value used is invalid
 */
public class InvalidIndexException extends ParseException {
    public InvalidIndexException(String message) {
        super(message);
    }
}
