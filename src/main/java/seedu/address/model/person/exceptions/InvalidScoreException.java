package seedu.address.model.person.exceptions;

/**
 * Signals that the score input by the user is invalid
 */
public class InvalidScoreException extends RuntimeException {
    private double maxScore;
    public InvalidScoreException(String message, double maxScore) {
        super(message);
    }
    public double getMaxScore() {
        return maxScore;
    }
}
