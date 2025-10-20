package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTimeslots;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.Timeslots;
import seedu.address.model.UserPrefs;

/**
 * Represents a storage for {@link Timeslots}.
 */
public interface TimeslotsStorage {

    /**
     * Returns the file path of the Timeslot data file.
     */
    Path getTimeslotsFilePath();

    /**
     * Returns Timeslot data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from preference file failed.
     */
    Optional<UserPrefs> readTimeslots() throws DataLoadingException;

    /**
     * Saves the given {@link seedu.address.model.ReadOnlyTimeslots} to the storage.
     * @param timeslots cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTimeslots(ReadOnlyTimeslots timeslots) throws IOException;

}
