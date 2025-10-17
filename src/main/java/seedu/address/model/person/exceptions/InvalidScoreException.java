package seedu.address.model.person.exceptions;

/**
 * Signals that the score input by the user is invalid
 */
public class InvalidScoreException extends RuntimeException {
    private double maxScore;
    /**
     * Constructs an exception with the following:
     * @param message that is to be shown
     * @param maxScore of exam that user attempted to grade
     */
    public InvalidScoreException(String message, double maxScore) {
        super(message);
        this.maxScore = maxScore;
    }
    public double getMaxScore() {
        return maxScore;
    }
}
