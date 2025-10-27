package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;

/**
 * Minimal tests for AddConsultationCommand
 */
public class AddConsultationCommandTest {

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
    }

    @Test
    public void execute_overlapsExistingTimeslot_throwsCommandException() {
        // existing generic timeslot 10:00-11:00
        LocalDateTime existingStart = LocalDateTime.parse("2025-10-01T10:00:00", Timeslot.FORMATTER);
        LocalDateTime existingEnd = LocalDateTime.parse("2025-10-01T11:00:00", Timeslot.FORMATTER);
        Timeslot existing = new Timeslot(existingStart, existingEnd);
        modelManager.addTimeslot(existing);

        // new consultation overlaps (10:30-11:30)
        LocalDateTime consStart = LocalDateTime.parse("2025-10-01T10:30:00", Timeslot.FORMATTER);
        LocalDateTime consEnd = LocalDateTime.parse("2025-10-01T11:30:00", Timeslot.FORMATTER);
        ConsultationTimeslot consultation = new ConsultationTimeslot(consStart, consEnd, "Alice");

        AddConsultationCommand cmd = new AddConsultationCommand(consultation);

        assertThrows(CommandException.class, () -> cmd.execute(modelManager));
    }

    @Test
    public void execute_duplicateConsultationSameIntervalAndStudent_throwsCommandException() {
        // existing consultation 14:00-15:00 with Bob
        LocalDateTime start = LocalDateTime.parse("2025-10-02T14:00:00", Timeslot.FORMATTER);
        LocalDateTime end = LocalDateTime.parse("2025-10-02T15:00:00", Timeslot.FORMATTER);
        ConsultationTimeslot existing = new ConsultationTimeslot(start, end, "Bob");
        modelManager.addTimeslot(existing);

        // attempt to add same interval + same student
        ConsultationTimeslot duplicate = new ConsultationTimeslot(start, end, "Bob");
        AddConsultationCommand cmd = new AddConsultationCommand(duplicate);

        assertThrows(CommandException.class, () -> cmd.execute(modelManager));
    }

    @Test
    public void execute_sameIntervalDifferentStudent_throwsCommandException() {
        // existing consultation 09:00-10:00 with Charlie
        LocalDateTime start = LocalDateTime.parse("2025-10-03T09:00:00", Timeslot.FORMATTER);
        LocalDateTime end = LocalDateTime.parse("2025-10-03T10:00:00", Timeslot.FORMATTER);
        ConsultationTimeslot existing = new ConsultationTimeslot(start, end, "Charlie");
        modelManager.addTimeslot(existing);

        // attempt to add same interval but different student -> should be rejected as timeslot collision
        ConsultationTimeslot otherStudent = new ConsultationTimeslot(start, end, "Dana");
        AddConsultationCommand cmd = new AddConsultationCommand(otherStudent);

        CommandException thrown = assertThrows(CommandException.class, () -> cmd.execute(modelManager));
        assertEquals(AddConsultationCommand.MESSAGE_DUPLICATE_TIMESLOT, thrown.getMessage());
    }

    @Test
    public void execute_nonOverlapping_addsSuccessfully() {
        // existing generic timeslot 08:00-09:00
        LocalDateTime existingStart = LocalDateTime.parse("2025-10-04T08:00:00", Timeslot.FORMATTER);
        LocalDateTime existingEnd = LocalDateTime.parse("2025-10-04T09:00:00", Timeslot.FORMATTER);
        Timeslot existing = new Timeslot(existingStart, existingEnd);
        modelManager.addTimeslot(existing);

        // new consultation 10:00-11:00 (no overlap)
        LocalDateTime consStart = LocalDateTime.parse("2025-10-04T10:00:00", Timeslot.FORMATTER);
        LocalDateTime consEnd = LocalDateTime.parse("2025-10-04T11:00:00", Timeslot.FORMATTER);
        ConsultationTimeslot consultation = new ConsultationTimeslot(consStart, consEnd, "Eve");

        AddConsultationCommand cmd = new AddConsultationCommand(consultation);

        assertDoesNotThrow(() -> cmd.execute(modelManager));
        // ensure it's present in the model
        assertTrue(modelManager.getTimeslots().getTimeslotList().contains(consultation));
    }

    @Test
    public void execute_adjacentTimeslot_allowed() {
        // existing generic timeslot 11:00-12:00
        LocalDateTime existingStart = LocalDateTime.parse("2025-10-05T11:00:00", Timeslot.FORMATTER);
        LocalDateTime existingEnd = LocalDateTime.parse("2025-10-05T12:00:00", Timeslot.FORMATTER);
        Timeslot existing = new Timeslot(existingStart, existingEnd);
        modelManager.addTimeslot(existing);

        // new consultation exactly after existing ends (12:00-13:00) -> should be allowed
        LocalDateTime consStart = LocalDateTime.parse("2025-10-05T12:00:00", Timeslot.FORMATTER);
        LocalDateTime consEnd = LocalDateTime.parse("2025-10-05T13:00:00", Timeslot.FORMATTER);
        ConsultationTimeslot consultation = new ConsultationTimeslot(consStart, consEnd, "Frank");

        AddConsultationCommand cmd = new AddConsultationCommand(consultation);

        assertDoesNotThrow(() -> cmd.execute(modelManager));
        assertTrue(modelManager.getTimeslots().getTimeslotList().contains(consultation));
    }
}
