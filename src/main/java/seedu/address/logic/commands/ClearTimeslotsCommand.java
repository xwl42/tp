package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears all timeslots from the model.
 */
public class ClearTimeslotsCommand extends Command {

    public static final String COMMAND_WORD = "clear-timeslots";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all stored timeslots.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "All timeslots have been cleared.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.saveAddressBook();
        model.clearTimeslots();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return (other == this) // short circuit if same object
                || (other instanceof ClearTimeslotsCommand); // all instances are equivalent
    }
}
