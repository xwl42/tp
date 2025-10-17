package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GradeCommand.MESSAGE_GRADE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_HUNDRED_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.InvalidExamNameException;
import seedu.address.model.person.exceptions.InvalidScoreException;
import seedu.address.testutil.PersonBuilder;

public class GradeCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validExam_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GradeMap gradeMap = new GradeMap();
        gradeMap.gradeExam("midterms", 30.0);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withGradeMap(gradeMap.toString())
                .build();
        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON, "midterms", 30.0);

        String expectedMessage = String.format(MESSAGE_GRADE_SUCCESS,
                "midterms",
                editedPerson.getName(),
                30.0);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person firstPersonExpected = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setPerson(firstPersonExpected, editedPerson);

        assertCommandSuccess(gradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidExamName_throwsCommandException() {
        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON, "quiz", 50.0);
        assertThrows(CommandException.class, () -> gradeCommand.execute(model));
    }

    @Test
    public void execute_invalidScore_throwsCommandException() throws InvalidExamNameException {
        // Build a person with a GradeMap that throws InvalidScoreException for out-of-range scores
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GradeMap gradeMap = new GradeMap();
        try {
            gradeMap.gradeExam("finals", 101.0);
        } catch (InvalidScoreException e) {
            // expected during actual command execution
        }

        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON, "finals", 101.0);
        assertThrows(CommandException.class, () -> gradeCommand.execute(model));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        GradeCommand gradeCommand = new GradeCommand(INDEX_HUNDRED_PERSON, "pe1", 70.0);
        assertThrows(CommandException.class, () -> gradeCommand.execute(model));
    }

    @Test
    public void equals() {
        final GradeCommand standardCommand = new GradeCommand(INDEX_FIRST_PERSON, "midterms", 80.0);

        // same values -> returns true
        GradeCommand commandWithSameValues = new GradeCommand(INDEX_FIRST_PERSON, "midterms", 80.0);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different type -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different person index -> returns false
        assertNotEquals(new GradeCommand(INDEX_SECOND_PERSON, "midterms", 80.0), standardCommand);

        // different exam name -> returns false
        assertNotEquals(new GradeCommand(INDEX_FIRST_PERSON, "finals", 80.0), standardCommand);

        // different score -> returns false
        assertNotEquals(new GradeCommand(INDEX_FIRST_PERSON, "midterms", 90.0), standardCommand);
    }
}
