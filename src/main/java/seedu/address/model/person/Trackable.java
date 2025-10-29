package seedu.address.model.person;

import java.util.List;

/**
 * Represents a data structure wrapper that is tracked on the person card
 */
public interface Trackable {
    public List<TrackerColour> getTrackerColours();
}

