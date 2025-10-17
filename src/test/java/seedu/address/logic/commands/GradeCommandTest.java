package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.GradeCommand.MESSAGE_GRADE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_HUNDRED_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.InvalidExamNameException;
import seedu.address.model.person.exceptions.InvalidScoreException;
import seedu.address.testutil.PersonBuilder;

public class GradeCommandTest {
    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validExam_success() throws Exception {
        // Arrange
        ModelStubWithTypicalPersons modelStub = new ModelStubWithTypicalPersons();
        Person firstPerson = modelStub.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        GradeMap updatedGradeMap = new GradeMap();
        updatedGradeMap.gradeExam("midterms", 30.0);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withGradeMap(updatedGradeMap.toString())
                .build();

        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON, "midterms", 30.0);
        String expectedMessage = String.format(MESSAGE_GRADE_SUCCESS,
                "midterms", editedPerson.getName(), 30.0);

        // Act
        CommandResult commandResult = gradeCommand.execute(modelStub);

        // Assert
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(editedPerson, modelStub.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()));
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
    private class ModelStubWithTypicalPersons extends ModelStub {
        private final ObservableList<Person> persons =
                javafx.collections.FXCollections.observableArrayList(getTypicalAddressBook().getPersonList());

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            int index = persons.indexOf(target);
            if (index == -1) {
                throw new AssertionError("Person not found in model stub.");
            }
            persons.set(index, editedPerson);
        }
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // no-op, required because GradeCommand calls this method
        }

    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
