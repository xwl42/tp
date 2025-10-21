package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTimeslots;

/**
 * A class to access Timeslots data stored as a json file on the hard disk.
 */
public class JsonTimeslotsStorage implements TimeslotsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTimeslotsStorage.class);

    private Path filePath;

    public JsonTimeslotsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTimeslotsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTimeslots> readTimeslots() throws DataLoadingException {
        return readTimeslots(filePath);
    }

    /**
     * Similar to {@link #readTimeslots()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTimeslots> readTimeslots(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTimeslots> jsonTimeslots = JsonUtil.readJsonFile(
                filePath, JsonSerializableTimeslots.class);
        if (!jsonTimeslots.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTimeslots.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTimeslots(ReadOnlyTimeslots timeslots) throws IOException {
        saveTimeslots(timeslots, filePath);
    }

    /**
     * Similar to {@link #saveTimeslots(ReadOnlyTimeslots)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTimeslots(ReadOnlyTimeslots timeslots, Path filePath) throws IOException {
        requireNonNull(timeslots);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTimeslots(timeslots), filePath);
    }

}
