package seedu.address.model.person;

import seedu.address.model.person.exceptions.InvalidScoreException;
import java.util.Optional;

/**
 * Represents a graded examination.
 */
public class Examination implements Gradeable {
    private static final String INVALID_SCORE_FORMAT =
            "%.1f is invalid as a score. Input a number from 0 to %.1f";

    private final String name;
    private final double maxScore;
    private Optional<Double> score;

    /**
     * Sets the fields accordingly.
     * @param name the name of the examination
     * @param maxScore the maximum achievable score for the examination
     */
    public Examination(String name, double maxScore) {
        this.name = name;
        this.maxScore = maxScore;
        this.score = Optional.empty();
    }

    @Override
    public double getScore() {
        return score.orElse(-1.0);
    }

    @Override
    public void setScore(double inputScore) throws InvalidScoreException {
        if (inputScore < 0 || inputScore > maxScore) {
            throw new InvalidScoreException(
                    String.format(INVALID_SCORE_FORMAT, inputScore, maxScore)
            );
        }
        this.score = Optional.of(inputScore);
    }

    @Override
    public String toString() {
        return String.format("%s: %s",
                name, score.map(Object::toString).orElse("NA"));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Examination)) {
            return false;
        }
        Examination other = (Examination) obj;
        return this.name.equals(other.name)
                && Double.compare(this.maxScore, other.maxScore) == 0
                && this.score.equals(other.score);
    }
}
