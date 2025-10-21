package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTimeslots;
import seedu.address.model.Timeslots;
import seedu.address.model.timeslot.Timeslot;

/**
 * An Immutable Timeslots that is serializable to JSON format.
 */
@JsonRootName(value = "timeslots")
class JsonSerializableTimeslots {

    public static final String MESSAGE_DUPLICATE_TIMESLOT = "Timeslots list contains duplicate timeslot(s).";

    private final List<JsonAdaptedTimeslot> timeslots = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTimeslots} with the given timeslots.
     */
    @JsonCreator
    public JsonSerializableTimeslots(@JsonProperty("timeslots") List<JsonAdaptedTimeslot> timeslots) {
        if (timeslots != null) {
            this.timeslots.addAll(timeslots);
        }
    }

    /**
     * Converts a given {@code ReadOnlyTimeslots} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTimeslots}.
     */
    public JsonSerializableTimeslots(ReadOnlyTimeslots source) {
        timeslots.addAll(source.getTimeslotList().stream()
                .map(JsonAdaptedTimeslot::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this timeslots into the model's {@code Timeslots} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Timeslots toModelType() throws IllegalValueException {
        Timeslots modelTimeslots = new Timeslots();
        for (JsonAdaptedTimeslot jsonAdaptedTimeslot : timeslots) {
            Timeslot timeslot = jsonAdaptedTimeslot.toModelType();
            if (modelTimeslots.hasTimeslot(timeslot)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TIMESLOT);
            }
            modelTimeslots.addTimeslot(timeslot);
        }
        return modelTimeslots;
    }

}
