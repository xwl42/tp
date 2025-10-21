package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTimeslots;

/**
 * Represents a storage for {@link seedu.address.model.Timeslots}.
 */
public interface TimeslotsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTimeslotsFilePath();

    /**
     * Returns Timeslots data as a {@link ReadOnlyTimeslots}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTimeslots> readTimeslots() throws DataLoadingException;

    /**
     * @see #getTimeslotsFilePath()
     */
    Optional<ReadOnlyTimeslots> readTimeslots(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTimeslots} to the storage.
     * @param timeslots cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTimeslots(ReadOnlyTimeslots timeslots) throws IOException;

    /**
     * @see #saveTimeslots(ReadOnlyTimeslots)
     */
    void saveTimeslots(ReadOnlyTimeslots timeslots, Path filePath) throws IOException;

}
