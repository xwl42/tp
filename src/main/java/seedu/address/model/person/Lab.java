package seedu.address.model.person;

/**
 * Represents a student's attendance status for a single lab session.
 */
public class Lab implements LabAttendance {
    public static final String MESSAGE_CONSTRAINTS = "Lab status must be y or n";
    public static final int LAB_WEEK_DIFFERENCE = 2;
    private final int labNumber;
    private boolean isAttended;
    private final boolean isPastWeek;

    /**
     * Constructs a {@code Lab} with the specified lab number and attendance initially set to false.
     * The lab's past week status is determined by comparing the lab week with the current week.
     *
     * @param labNumber The lab number (must be positive and not exceed {@code LabList.NUMBER_OF_LABS})
     * @param currentWeek The current week number in the semester
     */
    public Lab(int labNumber, int currentWeek) {
        assert labNumber > 0 : "Invalid lab number";
        assert labNumber <= LabList.NUMBER_OF_LABS : "Lab number exceeded the maximum amount";
        this.labNumber = labNumber;
        this.isAttended = false;
        this.isPastWeek = (labNumber + LAB_WEEK_DIFFERENCE < currentWeek);
    }

    @Override
    public void markAsAttended() throws IllegalStateException {
        if (isAttended) {
            throw new IllegalStateException("Lab Attendance has already been marked as attended");
        }
        isAttended = true;
    }

    @Override
    public void markAsAbsent() {
        if (!isAttended) {
            throw new IllegalStateException("Lab Attendance has already been marked as not attended");
        }
        isAttended = false;
    }

    @Override
    public boolean isAttended() {
        return isAttended;
    }

    public int getLabNumber() {
        return labNumber;
    }

    public String getStatus() {
        if (isAttended) {
            return "Y";
        } else if (isPastWeek) {
            return "A";
        } else {
            return "N";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Lab)) {
            return false;
        }

        Lab otherLab = (Lab) other;
        return this.isAttended == otherLab.isAttended
                && this.labNumber == otherLab.labNumber;
    }

    @Override
    public String toString() {
        return String.format("L%d: %s", labNumber, getStatus());
    }
}
