package seedu.address.logic.helpers;

/**
 * Simple data transfer object pairing a lab index with its status.
 * Immutable after construction.
 */
public class LabIndexStatus {
    private final String labIndex;
    private final String status;

    /**
     * Creates an instance with the given index and status.
     *
     * @param labIndex the lab index
     * @param status the status to associate with the lab
     */
    public LabIndexStatus(String labIndex, String status) {
        this.labIndex = labIndex;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getLabIndex() {
        return labIndex;
    }
}
