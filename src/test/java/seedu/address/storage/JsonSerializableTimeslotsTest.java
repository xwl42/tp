package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
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
    public void toModelType_duplicateTimeslots_throwsIllegalValueException() {
        Timeslots source = new Timeslots();
        Timeslot ts1 = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        Timeslot ts2 = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        source.addTimeslot(ts1);
        source.addTimeslot(ts2);

        JsonSerializableTimeslots json = new JsonSerializableTimeslots(source);
        assertThrows(IllegalValueException.class, json::toModelType);
    }
}
