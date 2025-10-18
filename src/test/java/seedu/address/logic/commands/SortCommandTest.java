package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.SortCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.sortcriterion.NameSortCriterion;
import seedu.address.model.person.sortcriterion.SortCriterion;
import seedu.address.model.person.sortcriterion.StudentIdSortCriterion;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private static final SortCriterion nameSortCriterion = new NameSortCriterion();
    private static final SortCriterion studentIdSortCriterion = new StudentIdSortCriterion();

    @Test
    public void execute() {
        final SortCriterion sortCriterion = new NameSortCriterion();

        assertCommandFailure(new SortCommand(sortCriterion), model, String.format(MESSAGE_ARGUMENTS, sortCriterion));
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = new SortCommand(nameSortCriterion);

        // Same values
        SortCommand commandWithSameValues =  new SortCommand(nameSortCriterion);
        assertEquals(standardCommand, commandWithSameValues);

        // Same object
        assertEquals(standardCommand, standardCommand);

        // Null
        assertNotEquals(standardCommand, null);

        // Different Command
        assertNotEquals(new ListCommand(), standardCommand);

        // Different criterion
        assertNotEquals(new SortCommand(studentIdSortCriterion), standardCommand);
    }
}
