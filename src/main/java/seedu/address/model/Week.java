package seedu.address.model;

/**
 * Represents the current week number in the semester.
 */
public class Week {
    public static final int MIN_WEEK = 0;
    public static final int MAX_WEEK = 13;

    public static final String MESSAGE_CONSTRAINTS =
            "Week number should be between " + MIN_WEEK + " and " + MAX_WEEK + " (inclusive)";

    private final int weekNumber;

    public Week(int weekNumber) {
        if (weekNumber < 0 || weekNumber > MAX_WEEK) {
            throw new IllegalStateException(MESSAGE_CONSTRAINTS);
        }
        this.weekNumber = weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    @Override
    public String toString() {
        return "Week " + weekNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Week)) {
            return false;
        }

        return this.weekNumber == ((Week) other).weekNumber;
    }
}

