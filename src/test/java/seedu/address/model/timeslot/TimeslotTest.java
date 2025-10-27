package seedu.address.model.timeslot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TimeslotTest {

    @Test
    public void overlap_detection_basic() {
        LocalDateTime aStart = LocalDateTime.of(2025, 10, 4, 9, 0);
        LocalDateTime aEnd = LocalDateTime.of(2025, 10, 4, 10, 0);
        LocalDateTime bStart = LocalDateTime.of(2025, 10, 4, 9, 30);
        LocalDateTime bEnd = LocalDateTime.of(2025, 10, 4, 10, 30);
        LocalDateTime cStart = LocalDateTime.of(2025, 10, 4, 10, 0);
        LocalDateTime cEnd = LocalDateTime.of(2025, 10, 4, 11, 0);

        Timeslot a = new Timeslot(aStart, aEnd);
        Timeslot b = new Timeslot(bStart, bEnd);
        Timeslot c = new Timeslot(cStart, cEnd);

        // a and b overlap
        assertTrue(a.getEnd().isAfter(b.getStart()) && a.getStart().isBefore(b.getEnd()));

        // a and c are adjacent (end == start) => not overlap by convention
        assertFalse(a.getEnd().isAfter(c.getStart()) && a.getStart().isBefore(c.getEnd()));
    }
}
