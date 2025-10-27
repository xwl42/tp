package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;

/**
 * Minimal tests for AddConsultationCommand overlapping and duplicate scenarios.
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
}
