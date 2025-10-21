package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.timeslot.Timeslot;

public class TimeslotTest {

    @Test
    public void constructor_invalidRange_throwsIllegalArgumentException() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 4, 13, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 4, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new Timeslot(start, end));
    }

    @Test
    public void equalsAndHashcode() {
        Timeslot a = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        Timeslot b = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        Timeslot c = new Timeslot(LocalDateTime.of(2025, 10, 4, 11, 0),
                LocalDateTime.of(2025, 10, 4, 12, 0));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
