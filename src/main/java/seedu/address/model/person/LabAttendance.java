package seedu.address.model.person;

/**
 * Represents a student's attendance status for a single lab session.
 */
public class LabAttendance implements Lab {
    private boolean hasAttended;

    /**
     * Constructs an {@code LabAttendance} with attendance initially set to false.
     */
    public LabAttendance() {
        this.hasAttended = false;
    }

    @Override
    public void markAsAttended() throws IllegalStateException {
        if (hasAttended) {
            throw new IllegalStateException("Lab Attendance has already been marked");
        }
        hasAttended = true;
    }

    @Override
    public boolean hasAttended() {
        return hasAttended;
    }

}
