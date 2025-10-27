package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.timeslot.Timeslot;

public class TimeslotsTest {

    @Test
    public void addTimeslot_overlapping_throwsIllegalArgumentException() {
        Timeslots timeslots = new Timeslots();
        LocalDateTime aStart = LocalDateTime.parse("2025-10-01T10:00:00", Timeslot.FORMATTER);
        LocalDateTime aEnd = LocalDateTime.parse("2025-10-01T11:00:00", Timeslot.FORMATTER);
        Timeslot a = new Timeslot(aStart, aEnd);
        timeslots.addTimeslot(a);

        // overlapping (starts before aEnd and ends after aStart)
        LocalDateTime bStart = LocalDateTime.parse("2025-10-01T10:30:00", Timeslot.FORMATTER);
        LocalDateTime bEnd = LocalDateTime.parse("2025-10-01T11:30:00", Timeslot.FORMATTER);
        Timeslot b = new Timeslot(bStart, bEnd);

        assertThrows(IllegalArgumentException.class, () -> timeslots.addTimeslot(b));
    }
}
