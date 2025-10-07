package seedu.address.model.person;

/**
 * Represents a collection of lab attendance records for a student across all lab sessions.
 */
public class LabAttendanceList {
    public static final int NUMBER_OF_LABS = 10;
    private final LabAttendance[] labAttendanceList;

    /**
     * Constructs a {@code LabAttendanceList} with all lab sessions initially marked as not attended.
     */
    public LabAttendanceList() {
        labAttendanceList = new LabAttendance[NUMBER_OF_LABS];
        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            labAttendanceList[i] =  new LabAttendance();
        }
    }

    /**
     * Marks the specified lab session as attended.
     * @param index the zero-based index of the lab session.
     */
    public void markLabAsAttended(int index) {
        if (index < 0 || index >= NUMBER_OF_LABS) {
            // throw exception
            // TODO
            return;
        }
        labAttendanceList[index].markAsAttended();
    }
}
