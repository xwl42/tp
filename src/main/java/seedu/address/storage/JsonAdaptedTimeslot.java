package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.timeslot.Timeslot;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link Timeslot}.
 */
class JsonAdaptedTimeslot {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Timeslot's %s field is missing!";

    private final String start;
    private final String end;

    @JsonCreator
    public JsonAdaptedTimeslot(@JsonProperty("start") String start,
                               @JsonProperty("end") String end) {
        this.start = start;
        this.end = end;
    }

    public JsonAdaptedTimeslot(Timeslot source) {
        this.start = source.getStart().format(Timeslot.FORMATTER);
        this.end = source.getEnd().format(Timeslot.FORMATTER);
    }

    public Timeslot toModelType() throws IllegalValueException {
        if (start == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start"));
        }
        if (end == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end"));
        }
        try {
            LocalDateTime s = LocalDateTime.parse(start, Timeslot.FORMATTER);
            LocalDateTime e = LocalDateTime.parse(end, Timeslot.FORMATTER);
            return new Timeslot(s, e);
        } catch (Exception ex) {
            throw new IllegalValueException(ex.getMessage());
        }
    }
}
