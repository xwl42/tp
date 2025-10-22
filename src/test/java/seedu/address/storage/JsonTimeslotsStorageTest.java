package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Timeslots;
import seedu.address.model.timeslot.Timeslot;

public class JsonTimeslotsStorageTest {

    @TempDir
    public Path testFolder;

    @Test
    public void saveAndReadTimeslots_success() throws Exception {
        Path abFile = testFolder.resolve("ab.json");
        Path prefsFile = testFolder.resolve("prefs.json");
        Path timeslotsFile = testFolder.resolve("timeslots.json");

        AddressBookStorage abStorage = new JsonAddressBookStorage(abFile);
        UserPrefsStorage prefsStorage = new JsonUserPrefsStorage(prefsFile);
        TimeslotsStorage timesStorage = new JsonTimeslotsStorage(timeslotsFile);

        StorageManager storage = new StorageManager(abStorage, prefsStorage, timesStorage);

        Timeslots toSave = new Timeslots();
        Timeslot ts = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        toSave.addTimeslot(ts);

        // save
        storage.saveTimeslots(toSave);

        // read back
        Optional<seedu.address.model.ReadOnlyTimeslots> read = storage.readTimeslots();
        assertTrue(read.isPresent());
        Timeslots readTimeslots = new Timeslots(read.get());
        assertTrue(readTimeslots.hasTimeslot(ts));
    }
}
