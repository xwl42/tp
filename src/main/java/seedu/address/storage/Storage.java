package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTimeslots;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component that aggregates address book, user prefs, and timeslots storage.
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    // ==== Timeslots persistence API ====

    /**
     * Returns the file path of the timeslots data file.
     */
    Path getTimeslotsFilePath();

    /**
     * Reads timeslots data from storage.
     *
     * @throws DataLoadingException if loading the data failed.
     */
    Optional<ReadOnlyTimeslots> readTimeslots() throws DataLoadingException;

    /**
     * Reads timeslots data from the specified file path.
     */
    Optional<ReadOnlyTimeslots> readTimeslots(Path filePath) throws DataLoadingException;

    /**
     * Saves the given timeslots to storage.
     *
     * @throws IOException if writing fails.
     */
    void saveTimeslots(ReadOnlyTimeslots timeslots) throws IOException;

    /**
     * Saves the given timeslots to the specified file path.
     */
    void saveTimeslots(ReadOnlyTimeslots timeslots, Path filePath) throws IOException;
}
