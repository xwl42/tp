package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Test
    public void mergeOverlappingTimeslots_emptyList_returnsEmpty() {
        List<Timeslot> empty = new ArrayList<>();
        List<LocalDateTime[]> merged = GetTimeslotCommand.mergeOverlappingTimeslots(empty);
        assertTrue(merged.isEmpty());
    }

    @Test
    public void mergeOverlappingTimeslots_overlappingAndSeparateIntervals_mergedCorrectly() {
        List<Timeslot> sorted = new ArrayList<>();
        // 4 Oct 2025 10:00 - 11:00
        sorted.add(new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 11, 0)));
        // 4 Oct 2025 10:30 - 12:00 (overlaps previous)
        sorted.add(new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 30),
                LocalDateTime.of(2025, 10, 4, 12, 0)));
        // 4 Oct 2025 13:00 - 14:00 (separate)
        sorted.add(new Timeslot(LocalDateTime.of(2025, 10, 4, 13, 0),
                LocalDateTime.of(2025, 10, 4, 14, 0)));

        List<LocalDateTime[]> merged = GetTimeslotCommand.mergeOverlappingTimeslots(sorted);

        assertEquals(2, merged.size());

        LocalDateTime[] first = merged.get(0);
        assertEquals(LocalDateTime.of(2025, 10, 4, 10, 0), first[0]);
        assertEquals(LocalDateTime.of(2025, 10, 4, 12, 0), first[1]);

        LocalDateTime[] second = merged.get(1);
        assertEquals(LocalDateTime.of(2025, 10, 4, 13, 0), second[0]);
        assertEquals(LocalDateTime.of(2025, 10, 4, 14, 0), second[1]);
    }
}
