package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.Timeslot;

public class ClearTimeslotsCommandTest {

    @Test
    public void execute_clearsTimeslots_success() throws Exception {
        ModelManager model = new ModelManager();
        Timeslot ts = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        model.addTimeslot(ts);
        // ensure precondition
        assertTrue(model.hasTimeslot(ts));

        ClearTimeslotsCommand cmd = new ClearTimeslotsCommand();
        CommandResult result = cmd.execute(model);

        assertEquals(ClearTimeslotsCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertFalse(model.hasTimeslot(ts));
    }

    @Test
    public void equals_behaviour() {
        ClearTimeslotsCommand cmd1 = new ClearTimeslotsCommand();
        ClearTimeslotsCommand cmd2 = new ClearTimeslotsCommand();

        // same object
        assertTrue(cmd1.equals(cmd1));

        // different instances but same semantics
        assertTrue(cmd1.equals(cmd2));

        // null and different type
        assertFalse(cmd1.equals(null));
        assertFalse(cmd1.equals("some string"));
    }
}
