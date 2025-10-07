package seedu.address.model.person;

public class LabAttendance {
    private boolean hasAttended;

    public LabAttendance() {
        this.hasAttended = false;
    }

    public void markAsAttended() {
        hasAttended = true;
    }
}
