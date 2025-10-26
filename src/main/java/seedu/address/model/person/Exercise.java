package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents an exercise.
 */
public class Exercise {
    public static final int EXERCISE_WEEK_DIFFERENCE = 2;

    private boolean isDone;
    private final int exerciseNumber;
    private boolean isPastWeek;
    private int currentWeek;

    /**
     * Creates an exercise object with the following parameters.
     * @param exerciseNumber the exercise number
     * @param isDone the completion status of the exercise
     */
    public Exercise(int exerciseNumber, boolean isDone) {
        assert exerciseNumber > 0 : "Invalid exercise number";
        this.exerciseNumber = exerciseNumber;
        this.isDone = isDone;
        this.currentWeek = 0;
        this.isPastWeek = false; // will update when currentWeek is set
    }

    @Override
    public String toString() {
        return String.format("ex %d: %s", exerciseNumber, getStatus());
    }

    public void markStatus(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setCurrentWeek(int week) {
        this.currentWeek = week;
        this.isPastWeek = (exerciseNumber + EXERCISE_WEEK_DIFFERENCE < currentWeek);
    }

    public Status getStatus() {
        if (isDone) {
            return Status.DONE;
        } else if (isPastWeek) {
            return Status.OVERDUE;
        } else {
            return Status.NOT_DONE;
        }
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
        return exerciseNumber == otherExercise.exerciseNumber && isDone == otherExercise.isDone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseNumber, isDone);
    }
}
