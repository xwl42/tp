package seedu.address.logic.helpers;


public class LabIndexStatus {
    private final String exerciseIndex;
    private final String status;

    public LabIndexStatus(String exerciseIndex, String status) {
        this.exerciseIndex = exerciseIndex;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getLabIndex() {
        return exerciseIndex;
    }
}
