package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.Timeslot;

public class BlockTimeslotCommandTest {

    @Test
    public void execute_addTimeslot_success() throws Exception {
        ModelManager model = new ModelManager();
        Timeslot ts = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        BlockTimeslotCommand cmd = new BlockTimeslotCommand(ts);

        CommandResult result = cmd.execute(model);

        assertTrue(model.hasTimeslot(ts));
        assertEquals(String.format(BlockTimeslotCommand.MESSAGE_SUCCESS, ts), result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateTimeslot_throwsCommandException() throws Exception {
        ModelManager model = new ModelManager();
        Timeslot ts = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        model.addTimeslot(ts); // pre-add

        BlockTimeslotCommand cmd = new BlockTimeslotCommand(ts);

        assertThrows(CommandException.class, () -> cmd.execute(model));
    }
}
