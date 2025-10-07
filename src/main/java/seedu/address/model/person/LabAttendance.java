package seedu.address.model.person;

/**
 * Represents a student's attendance status for a single lab session.
 */
public class LabAttendance {
    private boolean hasAttended;

    /**
     * Constructs an {@code LabAttendance} with attendance initially set to false.
     */
    public LabAttendance() {
        this.hasAttended = false;
    }

    /**
     * Marks the lab as attended.
     */
    public void markAsAttended() {
        if (hasAttended) {
            // throw exception
            // TODO
            return;
        }
        hasAttended = true;
    }

    /**
     * Returns whether this lab has been marked as attended.
     *
     * @return {@code true} if the lab has been marked as attended;
     *         {@code false} otherwise
     */
    public boolean hasAttended() {
        return hasAttended;
    }

}
