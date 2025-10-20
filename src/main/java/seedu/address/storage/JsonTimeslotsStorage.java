package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTimeslots;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.Timeslots;
import seedu.address.model.UserPrefs;

/**
 * A class to access Timeslots stored in the hard disk as a json file
 */
public class JsonTimeslotsStorage implements TimeslotsStorage {

    private Path filePath;

    public JsonTimeslotsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTimeslotsFilePath() {
        return filePath;
    }

    @Override
    public Optional<Timeslots> readTimeslots() throws DataLoadingException {
        return readTimeslots(filePath);
    }

    /**
     * Similar to {@link #readTimeslots()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<Timeslots> readTimeslots(Path prefsFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(prefsFilePath, Timeslots.class);
    }

    @Override
    public void saveTimeslots(ReadOnlyTimeslots timeslots) throws IOException {
        JsonUtil.saveJsonFile(timeslots, filePath);
    }

}
