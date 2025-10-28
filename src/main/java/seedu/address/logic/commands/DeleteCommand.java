package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.MultiIndex;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes one or more students identified using their displayed indexes from LambdaLab.
 */
public class DeleteCommand extends MultiIndexCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more students identified by their index numbers in the displayed student list.\n"
            + "Parameters: INDEX... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1:5";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student(s):\n%1$s";

    /**
     * Constructs a {@code DeleteCommand} with multiple indices.
     */
    public DeleteCommand(MultiIndex multiIndex) {
        super(multiIndex);
    }

    @Override
    protected Person applyActionToPerson(Model model, Person personToDelete) {
        requireNonNull(model);
        model.deletePerson(personToDelete);
        return personToDelete;
    }

    @Override
    protected CommandResult buildResult(List<Person> deletedPersons) {
        String deletedList = deletedPersons.stream()
                .map(Messages::format)
                .collect(Collectors.joining(","));
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteCommand
                && multiIndex.equals(((DeleteCommand) other).multiIndex));
    }

    @Override
    public String toString() {
        return "DeleteCommand{" + "multiIndex=" + multiIndex + "}";
    }
}
