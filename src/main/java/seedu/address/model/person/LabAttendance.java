package seedu.address.model.person;

/**
 * Represents a lab attendance status.
 */
public interface LabAttendance {

    /**
     * Marks this lab as attended.
     */
    public void markAsAttended();

    /**
     * Marks this lab as not attended.
     */
    public void markAsAbsent();

    /**
     * Returns whether this lab has been marked as attended.
     *
     * @return {@code true} if the lab has been marked as attended;
     *         {@code false} otherwise
     */
    public boolean isAttended();
}
