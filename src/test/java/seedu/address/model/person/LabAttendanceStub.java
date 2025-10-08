package seedu.address.model.person;

/**
 * A stub implementation of {@code Lab} for testing purposes.
 * Provides a simple in-memory tracking of attendance status.
 */
public class LabAttendanceStub implements Lab {
    private boolean hasAttended = false;

    @Override
    public void markAsAttended() {
        hasAttended = true;
    }

    @Override
    public boolean hasAttended() {
        return hasAttended;
    }
}
