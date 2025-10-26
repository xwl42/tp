package seedu.address.model.person;

/**
 * A stub implementation of {@code Lab} for testing purposes.
 * Provides a simple in-memory tracking of attendance status.
 */
public class LabAttendanceStub implements LabAttendance {
    private boolean isAttended = false;

    @Override
    public void markAsAttended() {
        isAttended = true;
    }

    @Override
    public void markAsAbsent() {
        isAttended = false;
    }

    @Override
    public boolean isAttended() {
        return isAttended;
    }

    @Override
    public int getLabNumber() {
        return 0;
    }

    @Override
    public String getStatus() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "L" + (isAttended ? "Y" : "N");
    }
}
