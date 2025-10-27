package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;

/**
 * Tests for GetConsultationsCommand.
 */
public class GetConsultationsCommandTest {

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
        // ensure clean timeslot state for each test
        // ModelManager initializes with empty Timeslots; no explicit clear necessary.
    }

    @Test
    public void execute_noConsultations_returnsNoConsultationsMessage() throws Exception {
        GetConsultationsCommand cmd = new GetConsultationsCommand();
        CommandResult result = cmd.execute(modelManager);

        // when there are no consultations, command returns a simple message and no ranges payload
        assertEquals("No consultations found.", result.getFeedbackToUser());
        assertNull(result.getTimeslotRanges());
    }

    @Test
    public void execute_withConsultations_returnsOnlyConsultationRanges() throws Exception {
        // add a generic timeslot (should be ignored by get-consultations)
        Timeslot generic = new Timeslot(
                LocalDateTime.of(2025, 10, 1, 9, 0),
                LocalDateTime.of(2025, 10, 1, 10, 0));
        modelManager.addTimeslot(generic);

        // add two consultations (non-overlapping so both should appear)
        ConsultationTimeslot c1 = new ConsultationTimeslot(
                LocalDateTime.of(2025, 10, 1, 11, 0),
                LocalDateTime.of(2025, 10, 1, 12, 0),
                "Alice");
        ConsultationTimeslot c2 = new ConsultationTimeslot(
                LocalDateTime.of(2025, 10, 2, 14, 0),
                LocalDateTime.of(2025, 10, 2, 15, 0),
                "Bob");

        modelManager.addTimeslot(c1);
        modelManager.addTimeslot(c2);

        GetConsultationsCommand cmd = new GetConsultationsCommand();
        CommandResult result = cmd.execute(modelManager);

        // Should have a merged ranges payload (list of LocalDateTime[]), one entry per consultation above
        List<LocalDateTime[]> ranges = result.getTimeslotRanges();
        assertNotNull(ranges);
        // Expect two ranges (c1 and c2)
        assertEquals(2, ranges.size());

        LocalDateTime[] range1 = ranges.get(0);
        LocalDateTime[] range2 = ranges.get(1);

        assertEquals(c1.getStart(), range1[0]);
        assertEquals(c1.getEnd(), range1[1]);

        assertEquals(c2.getStart(), range2[0]);
        assertEquals(c2.getEnd(), range2[1]);

        // feedback contains a readable listing (sanity-check)
        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Consultations"));
        assertTrue(feedback.contains("11:00") || feedback.contains("11:00"));
    }
}
