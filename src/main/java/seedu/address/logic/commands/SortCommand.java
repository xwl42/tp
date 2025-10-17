package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_CRITERION;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts the students in the address book by a specified criterion.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the students in the display list "
            + "by the specified criterion"
            + "Parameters: " + PREFIX_SORT_CRITERION + "CRITERION\n"
            + "Example: " + COMMAND_WORD + "name"; // TODO: Add example criterion

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Sort command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
