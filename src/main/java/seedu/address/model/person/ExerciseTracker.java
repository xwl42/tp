package seedu.address.model.person;

import static seedu.address.model.person.Status.NOT_DONE;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;

/**
 * Represents a Person's address in the address book.
 */
public class ExerciseTracker implements Comparable<ExerciseTracker> {

    public static final String MESSAGE_CONSTRAINTS = "Exercise tracker takes in statuses";
    public static final int NUMBER_OF_EXERCISES = 10;
    public static final double WEIGHT_DONE = 1;
    public static final double WEIGHT_OVERDUE = -0.5;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String INDEX_OUT_OF_FOUNDS_FORMAT = "Index should be between 0 and %s";


    private ArrayList<Exercise> exercises = new ArrayList<>();

    /**
     * Initialises statuses to all be not done
     */
    public ExerciseTracker() {
        for (int i = 0; i < NUMBER_OF_EXERCISES; i++) {
            exercises.add(new Exercise(i, false));
        }
        assert exercises.size() == NUMBER_OF_EXERCISES : "Exercise tracker must have exactly 10 exercises";
    }
    /**
     * Initializes exercises using a list of statuses.
     * Each index corresponds to an exercise number.
     */
    public ExerciseTracker(ArrayList<Boolean> isDoneList) {
        assert isDoneList != null : "Statuses list must not be null";
        if (isDoneList.size() > NUMBER_OF_EXERCISES) {
            throw new IllegalArgumentException("Too many statuses! Expected at most " + NUMBER_OF_EXERCISES);
        }
        this.exercises = new ArrayList<>();
        for (int i = 0; i < isDoneList.size(); i++) {
            exercises.add(new Exercise(i, isDoneList.get(i)));
        }
        for (int i = isDoneList.size(); i < NUMBER_OF_EXERCISES; i++) {
            exercises.add(new Exercise(i, false));
        }
    }

    @Override
    public String toString() {
        return exercises.stream()
                .map(Exercise::toString)
                .collect(Collectors.joining(" "));
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
    public ArrayList<Boolean> getIsDoneList() {
        assert exercises != null && !exercises.isEmpty() : "Exercises must be initialized";
        return exercises.stream()
                .map(Exercise::isDone)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Marks the exercise at the given index with the status
     * @param index of the exercise
     * @param isDone to mark the exercise with
     */
    public void markExercise(Index index, boolean isDone) {
        logger.info(String.format("Marking ex %d with %s", index.getOneBased(), isDone));
        if (index.getZeroBased() < 0 || index.getZeroBased() >= NUMBER_OF_EXERCISES) {
            throw new IndexOutOfBoundsException(
                    String.format(INDEX_OUT_OF_FOUNDS_FORMAT,
                            NUMBER_OF_EXERCISES - 1)
            );
        }
        exercises.get(index.getZeroBased()).markStatus(isDone);
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
        ArrayList<Boolean> copiedStatuses = new ArrayList<>(this.getIsDoneList());
        return new ExerciseTracker(copiedStatuses);
    }

    public List<Status> getStatuses() {
        return exercises.stream().map(Exercise::getStatus).toList();
    }
}

