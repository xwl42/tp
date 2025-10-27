package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;

public class JsonAdaptedTimeslotTest {

    @Test
    public void toModelType_validGenericTimeslot_success() throws Exception {
        LocalDateTime start = LocalDateTime.of(2025, 10, 4, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 4, 11, 0);
        String sStart = start.format(Timeslot.FORMATTER);
        String sEnd = end.format(Timeslot.FORMATTER);

        // null student name -> generic Timeslot
        JsonAdaptedTimeslot adapted = new JsonAdaptedTimeslot(sStart, sEnd, null);
        Timeslot model = adapted.toModelType();
        assertTrue(model instanceof Timeslot);
    }

    @Test
    public void toModelType_validConsultationTimeslot_success() throws Exception {
        LocalDateTime start = LocalDateTime.of(2025, 10, 4, 12, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 4, 13, 0);
        String sStart = start.format(Timeslot.FORMATTER);
        String sEnd = end.format(Timeslot.FORMATTER);

        JsonAdaptedTimeslot adapted = new JsonAdaptedTimeslot(sStart, sEnd, "Student");
        assertTrue(adapted.toModelType() instanceof ConsultationTimeslot);
    }

    @Test
    public void toModelType_invalidDatetime_throwsIllegalValueException() {
        JsonAdaptedTimeslot adapted = new JsonAdaptedTimeslot("not-a-date", "also-bad", null);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }
}
