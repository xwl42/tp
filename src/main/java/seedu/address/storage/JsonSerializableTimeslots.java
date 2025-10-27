package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTimeslots;
import seedu.address.model.Timeslots;
import seedu.address.model.timeslot.Timeslot;

/**
 * An Immutable Timeslots that is serializable to JSON format.
 */
public class JsonSerializableTimeslots {

    private final List<JsonAdaptedTimeslot> times = new ArrayList<>();

    @JsonCreator
    public JsonSerializableTimeslots(@JsonProperty("timeslots") List<JsonAdaptedTimeslot> times) {
        if (times != null) {
            this.times.addAll(times);
        }
    }

    /**
     * Converts a given {@code ReadOnlyTimeslots} into this class for Jackson use.
     */
    public JsonSerializableTimeslots(ReadOnlyTimeslots source) {
        times.addAll(source.getTimeslotList().stream()
                .map(JsonAdaptedTimeslot::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this JSON-friendly timeslots object into the model's {@code Timeslots} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Timeslots toModelType() throws IllegalValueException {
        Timeslots timeslots = new Timeslots();
        for (JsonAdaptedTimeslot jsonAdaptedTimeslot : times) {
            Timeslot modelTimeslot = jsonAdaptedTimeslot.toModelType();
            timeslots.addTimeslot(modelTimeslot);
        }
        return timeslots;
    }
}
