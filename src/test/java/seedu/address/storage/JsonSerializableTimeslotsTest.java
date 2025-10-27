package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Timeslots;
import seedu.address.model.timeslot.Timeslot;

public class JsonSerializableTimeslotsTest {

    @Test
    public void toModelType_roundTrip_success() throws Exception {
        Timeslots source = new Timeslots();
        Timeslot ts = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        source.addTimeslot(ts);

        JsonSerializableTimeslots json = new JsonSerializableTimeslots(source);
        Timeslots model = json.toModelType();
        assertTrue(model.hasTimeslot(ts));
    }

    @Test
    public void toModelType_duplicateTimeslots_throwsIllegalArgumentException() {
        // Build JSON-adapted timeslots containing two identical entries (simulates duplicate entries in file)
        LocalDateTime start = LocalDateTime.of(2025, 10, 4, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 4, 13, 0);
        String sStart = start.format(Timeslot.FORMATTER);
        String sEnd = end.format(Timeslot.FORMATTER);

        JsonAdaptedTimeslot j1 = new JsonAdaptedTimeslot(sStart, sEnd, null);
        JsonAdaptedTimeslot j2 = new JsonAdaptedTimeslot(sStart, sEnd, null);
        List<JsonAdaptedTimeslot> list = Arrays.asList(j1, j2);

        JsonSerializableTimeslots json = new JsonSerializableTimeslots(list);
        Exception e = assertThrows(IllegalArgumentException.class, json::toModelType);
        // sanity-check message to ensure it's the expected overlap/duplicate reason
        assertTrue(e.getMessage().toLowerCase().contains("overlap") || e.getMessage().toLowerCase().contains("duplicate"));
    }
}
