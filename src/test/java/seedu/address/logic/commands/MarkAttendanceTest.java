package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LAB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_HUNDRED_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LAB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.MultiIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.LabAttendanceList;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MarkAttendanceTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markLab_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LabAttendanceList labAttendanceList = new LabList();
        labAttendanceList.markLabAsAttended(0);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withLabAttendanceList(labAttendanceList.toString()).build();

        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                new MultiIndex(INDEX_FIRST_PERSON), INDEX_FIRST_LAB, true);

        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS,
                INDEX_FIRST_LAB.getOneBased(), firstPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                new MultiIndex(INDEX_HUNDRED_PERSON), INDEX_FIRST_LAB, true);
        assertThrows(CommandException.class, () -> markAttendanceCommand.execute(model));
    }

    @Test
    public void execute_labAlreadyMarked_compilesMessageSuccessfully() throws CommandException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LabAttendanceList labAttendanceList = new LabList();
        labAttendanceList.markLabAsAttended(0);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withLabAttendanceList(labAttendanceList.toString()).build();
        model.setPerson(firstPerson, editedPerson);

        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                new MultiIndex(INDEX_FIRST_PERSON), INDEX_FIRST_LAB, true);

        CommandResult result = markAttendanceCommand.execute(model);
        String expectedMessage = String.format(
                MarkAttendanceCommand.MESSAGE_FAILURE_ALREADY_ATTENDED,
                INDEX_FIRST_LAB.getOneBased(),
                firstPerson.getName());
        assertEquals(expectedMessage.trim(), result.getFeedbackToUser().trim());
    }


    @Test
    public void equals() {
        final MarkAttendanceCommand standardCommand = new MarkAttendanceCommand(
                new MultiIndex(INDEX_FIRST_PERSON), INDEX_FIRST_LAB, true);

        // same values -> returns true
        MarkAttendanceCommand commandWithSameValues = new MarkAttendanceCommand(
                new MultiIndex(INDEX_FIRST_PERSON), INDEX_FIRST_LAB, true);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different index -> returns false
        assertNotEquals(new MarkAttendanceCommand(
                new MultiIndex(INDEX_SECOND_PERSON), INDEX_FIRST_LAB, true), standardCommand);

        // different remark -> returns false
        assertNotEquals(new MarkAttendanceCommand(
                new MultiIndex(INDEX_FIRST_PERSON), INDEX_SECOND_LAB, true), standardCommand);
    }
}
