package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.timeslot.Timeslot;

/**
 * Unmodifiable view of a timeslots list.
 */
public interface ReadOnlyTimeslots {
    ObservableList<Timeslot> getTimeslotList();
}
