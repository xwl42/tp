package seedu.address.model.person;

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
