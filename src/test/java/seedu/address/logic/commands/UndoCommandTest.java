package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

public class UndoCommandTest {

    @Test
    public void execute_undoAvailable_success() throws Exception {
        ModelStubWithUndoAvailable modelStub = new ModelStubWithUndoAvailable();

        CommandResult commandResult = new UndoCommand().execute(modelStub);

        assertEquals(UndoCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertTrue(modelStub.undoAddressBookCalled);
    }

    @Test
    public void execute_noCommandToUndo_throwsCommandException() {
        ModelStubNoUndoAvailable modelStub = new ModelStubNoUndoAvailable();
        UndoCommand undoCommand = new UndoCommand();

        assertThrows(CommandException.class, UndoCommand.MESSAGE_FAILURE, () -> undoCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        UndoCommand undoCommand1 = new UndoCommand();
        UndoCommand undoCommand2 = new UndoCommand();

        // same object -> returns true
        assertTrue(undoCommand1.equals(undoCommand1));

        // different objects but same type -> returns true (all UndoCommands are equal)
        assertTrue(undoCommand1.equals(undoCommand2));

        // different types -> returns false
        assertFalse(undoCommand1.equals(1));

        // null -> returns false
        assertFalse(undoCommand1.equals(null));

        // different command type -> returns false
        assertFalse(undoCommand1.equals(new ListCommand()));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
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

        @Override
        public void saveAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub where undo is available.
     */
    private class ModelStubWithUndoAvailable extends ModelStub {
        private boolean undoAddressBookCalled = false;

        @Override
        public boolean canUndoAddressBook() {
            return true;
        }

        @Override
        public void undoAddressBook() {
            undoAddressBookCalled = true;
        }
    }

    /**
     * A Model stub where no undo is available.
     */
    private class ModelStubNoUndoAvailable extends ModelStub {
        @Override
        public boolean canUndoAddressBook() {
            return false;
        }
    }
}
