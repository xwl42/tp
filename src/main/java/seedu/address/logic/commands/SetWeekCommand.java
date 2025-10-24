package seedu.address.logic.commands;


import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class SetWeekCommand extends Command {
    public static final String COMMAND_WORD = "set-week";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the current week for the semester\n"
            + "Parameters: WEEK_NUMBER (must be between 0 and 13)\n"
            + "Example: " + COMMAND_WORD + " 7";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Hello boss");
    }
}
