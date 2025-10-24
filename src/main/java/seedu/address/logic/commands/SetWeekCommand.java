package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Week;

public class SetWeekCommand extends Command {
    public static final String COMMAND_WORD = "set-week";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the current week for the semester\n"
            + "Parameters: WEEK_NUMBER (must be between 0 and 13)\n"
            + "Example: " + COMMAND_WORD + " 7";

    private final Week currentWeek;

    public SetWeekCommand(Week currentWeek) {
        requireNonNull(currentWeek);

        this.currentWeek = currentWeek;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setCurrentWeek(currentWeek);
        throw new CommandException(currentWeek.toString());
    }
}
