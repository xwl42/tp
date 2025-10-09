package seedu.address.model.person;

/**
 * statuses for exercises
 */
public enum Status {
    DONE,
    NOT_DONE,
    IN_PROGRESS,
    OVERDUE;

    @Override
    public String toString() {
        return name().substring(0, 1);
    }

    /**
     * Converts first letter of status to status object
     * @param code first letter of status
     * @return status represented by code
     */
    public static Status fromString(String code) {
        switch (code.toUpperCase()) {
        case "D": return DONE;
        case "N": return NOT_DONE;
        case "I": return IN_PROGRESS;
        case "O": return OVERDUE;
        default:
            // fallback for full names
            return Status.valueOf(code.toUpperCase());
        }
    }
}
