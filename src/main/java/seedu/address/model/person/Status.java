package seedu.address.model.person;

/**
 * statuses for exercises
 */
public enum Status {
    DONE,
    NOT_DONE,
    OVERDUE;

    @Override
    public String toString() {
        return name().substring(0, 1);
    }
}
