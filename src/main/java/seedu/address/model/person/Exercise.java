package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents an exercise
 */
public class Exercise {
    private Status status;
    private final int exerciseNumber;
    /**
     * Creates an exercise object with the following parameters
     * @param exerciseNumber is the exercise number
     * @param status is the status of the exercise
     */
    public Exercise(int exerciseNumber, Status status) {
        assert exerciseNumber > 0 : "Invalid lab number";
        this.exerciseNumber = exerciseNumber;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("ex %d: %s", exerciseNumber, status);
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
        return exerciseNumber == otherExercise.exerciseNumber && status == otherExercise.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseNumber, status);
    }


    public Status getStatus() {
        return status;
    }
}
