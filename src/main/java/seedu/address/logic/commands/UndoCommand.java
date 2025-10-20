package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the most recent command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Successfully undone!";
    public static final String MESSAGE_FAILURE = "No command to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
