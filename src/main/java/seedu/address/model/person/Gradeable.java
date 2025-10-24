package seedu.address.model.person;

import seedu.address.model.person.exceptions.InvalidScoreException;

/**
 * Represents an object that has a grade field.
 */
public interface Gradeable {
    double getScore();
    void setScore(double score) throws InvalidScoreException;
    void setPercentageScore(double score) throws InvalidScoreException;
}
