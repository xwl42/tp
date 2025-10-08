package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class MarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "markA";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the specific lab of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LAB_NUMBER + "[LAB NUMBER]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LAB_NUMBER + "1";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
