package seedu.address.model.person;

import static seedu.address.model.person.Status.NOT_DONE;

import java.util.ArrayList;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;

/**
 * Represents a Person's address in the address book.
 */
public class ExerciseTracker implements Comparable<ExerciseTracker> {

    public static final String MESSAGE_CONSTRAINTS = "Exercise tracker takes in statuses";
    public static final int NUMBER_OF_EXERCISES = 10;
    public static final double WEIGHT_DONE = 1;
    public static final double WEIGHT_OVERDUE = -0.5;

    private ArrayList<Exercise> exercises = new ArrayList<>();

    /**
     * Initialises statuses to all be not done
     */
    public ExerciseTracker() {
        for (int i = 0; i < NUMBER_OF_EXERCISES; i++) {
            exercises.add(new Exercise(i, NOT_DONE));;
        }
    }
    /**
     * Initializes exercises using a list of statuses.
     * Each index corresponds to an exercise number.
     */
    public ExerciseTracker(ArrayList<Status> statuses) {
        if (statuses.size() != NUMBER_OF_EXERCISES) {
            throw new IllegalArgumentException("Expected " + NUMBER_OF_EXERCISES + " statuses, got " + statuses.size());
        }
        exercises = new ArrayList<>();
        for (int i = 0; i < statuses.size(); i++) {
            exercises.add(new Exercise(i, statuses.get(i)));
        }
    }

    @Override
    public String toString() {
        return exercises.stream().map(Exercise::toString).collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ExerciseTracker)) {
            return false;
        }
        ExerciseTracker otherTracker = (ExerciseTracker) other;
        return exercises.equals(otherTracker.exercises);
    }

    @Override
    public int hashCode() {
        return exercises.hashCode();
    }

    public ArrayList<Status> getStatuses() {
        return exercises.stream()
                .map(Exercise::getStatus)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Marks the exercise at the given index with the status
     * @param index of the exercise
     * @param status to mark the exercise with
     */
    public void markExercise(Index index, Status status) {
        if (index.getZeroBased() < 0 || index.getZeroBased() >= NUMBER_OF_EXERCISES) {
            throw new IndexOutOfBoundsException("Index should be between 0 and " + (NUMBER_OF_EXERCISES - 1));
        }
        exercises.get(index.getZeroBased()).markStatus(status);
    }

    /**
     * Calculate a student's exercise progress as percentage
     * @return the progress between -50.0 and 100.0.
     */
    public double calculateProgress() {
        double count = 0;
        for (int i = 0; i < NUMBER_OF_EXERCISES; i++) {
            Status status = exercises.get(i).getStatus();
            switch (status) {
            case DONE:
                count += WEIGHT_DONE;
                break;
            case OVERDUE:
                count += WEIGHT_OVERDUE;
                break;
            case NOT_DONE:
                break;

            default:
                throw new IllegalStateException("Unexpected status: " + status);
            }
        }
        return count / NUMBER_OF_EXERCISES * 100.0;
    }

    @Override
    public int compareTo(ExerciseTracker other) {
        return Double.compare(this.calculateProgress(), other.calculateProgress());
    }

    /**
     * Returns true if a given string is a valid exercise tracker format.
     */
    public static boolean isValidExerciseTracker(String exerciseTrackerString) {
        if (exerciseTrackerString == null) {
            return false;
        }

        String trimmed = exerciseTrackerString.trim();
        String[] parts = trimmed.split("\\s+");

        // Each exercise entry has 3 parts (ex, <index>:, <status>)
        if (parts.length != NUMBER_OF_EXERCISES * 3) {
            return false;
        }

        for (int i = 0; i < NUMBER_OF_EXERCISES; i++) {
            String exKeyword = parts[i * 3];
            String indexWithColon = parts[i * 3 + 1];
            String status = parts[i * 3 + 2];

            // Must start with "ex"
            if (!exKeyword.equals("ex")) {
                return false;
            }

            // Must match index with colon, e.g. "0:", "1:", ...
            if (!indexWithColon.equals(i + ":")) {
                return false;
            }

            // Valid statuses only
            if (!status.equals("N") && !status.equals("D")
                    && !status.equals("I") && !status.equals("O")) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a deep copy of this ExerciseTracker.
     * @return a new ExerciseTracker with copied data
     */
    public ExerciseTracker copy() {
        ArrayList<Status> copiedStatuses = new ArrayList<>(this.getStatuses());
        return new ExerciseTracker(copiedStatuses);
    }
}

