package seedu.address.model.timeslot;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a timeslot with a start and end time.
 * Immutable.
 */
public class Timeslot {

    // Use ISO for storage/serialization so existing JSON files remain compatible.
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Separate, user-friendly formatter for display in the UI and user-facing messages.
    public static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm");

    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates a Timeslot with the given start and end time.
     *
     * @param start must not be null.
     * @param end must not be null and must be after the start time.
     */
    public Timeslot(LocalDateTime start, LocalDateTime end) {
        requireNonNull(start);
        requireNonNull(end);
        if (end.isBefore(start) || end.equals(start)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return String.format("Timeslot[Start: %s, End: %s]", start.format(FORMATTER), end.format(FORMATTER));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Timeslot)) {
            return false;
        }
        Timeslot otherTs = (Timeslot) other;
        return start.equals(otherTs.start) && end.equals(otherTs.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
