package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.Timeslot;

public class GetTimeslotCommandTest {

    @Test
    public void execute_returnsTimeslotRangesFromModel() throws Exception {
        ModelManager model = new ModelManager();
        Timeslot ts = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        model.addTimeslot(ts);

        GetTimeslotCommand cmd = new GetTimeslotCommand();
        CommandResult result = cmd.execute(model);

        List<LocalDateTime[]> ranges = result.getTimeslotRanges();
        assertNotNull(ranges);
        // expect at least one range containing our timeslot's start
        boolean found = ranges.stream().anyMatch(arr -> arr != null && arr[0].equals(ts.getStart()));
        assertEquals(true, found);
    }
}
