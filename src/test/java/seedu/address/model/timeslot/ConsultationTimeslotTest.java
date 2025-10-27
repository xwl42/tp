package seedu.address.model.timeslot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ConsultationTimeslotTest {

    @Test
    public void studentName_and_equality() {
        LocalDateTime s = LocalDateTime.of(2025, 10, 4, 10, 0);
        LocalDateTime e = LocalDateTime.of(2025, 10, 4, 11, 0);

        ConsultationTimeslot c1 = new ConsultationTimeslot(s, e, "Alice");
        ConsultationTimeslot c2 = new ConsultationTimeslot(s, e, "Alice");
        ConsultationTimeslot c3 = new ConsultationTimeslot(s, e, "Bob");

        // getters
        assertEquals("Alice", c1.getStudentName());
        assertEquals(s, c1.getStart());
        assertEquals(e, c1.getEnd());

        // equality semantics (same interval + same student -> equal)
        assertEquals(c1, c2);
        // different student name -> not equal (even if interval same)
        assertNotEquals(c1, c3);
    }
}
