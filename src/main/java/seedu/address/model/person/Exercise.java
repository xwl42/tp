package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents an exercise
 */
public class Exercise {
    private Status status;
    private final int number;
    /**
     * Creates an exercise object with the following parameters
     * @param number is the exercise number
     * @param status is the status of the exercise
     */
    public Exercise(int number, Status status) {
        this.number = number;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("ex %d: %s", number, status);
    }

    public void markStatus(Status status) {
        this.status = status;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Exercise)) {
            return false;
        }
        Exercise otherExercise = (Exercise) other;
        return number == otherExercise.number && status == otherExercise.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, status);
    }


    public Status getStatus() {
        return status;
    }
}
