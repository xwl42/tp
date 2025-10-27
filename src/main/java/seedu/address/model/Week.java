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

    /**
     * Constructs a {@code Week} with the specified week number.
     *
     * @param weekNumber The week number in the semester (must be between 0 and 13 inclusive)
     * @throws IllegalStateException if the week number is not between {@code MIN_WEEK} and {@code MAX_WEEK}
     */
    public Week(int weekNumber) {
        if (weekNumber < 0 || weekNumber > MAX_WEEK) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
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

