package seedu.address.logic.helpers;

import seedu.address.model.person.Status;

public class ExerciseIndexStatus {
    private final String exerciseIndex;
    private final Status status;

    public ExerciseIndexStatus(String exerciseIndex, Status status) {
        this.exerciseIndex = exerciseIndex;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getExerciseIndex() {
        return exerciseIndex;
    }
}
