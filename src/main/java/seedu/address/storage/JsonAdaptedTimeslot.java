package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;

/**
 * Jackson-friendly version of {@link Timeslot}.
 */
public class JsonAdaptedTimeslot {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Timeslot's %s field is missing!";

    private final String start;
    private final String end;
    private final String studentName;

    /**
     * Constructs a {@code JsonAdaptedTimeslot} with the given fields.
     */
    @JsonCreator
    public JsonAdaptedTimeslot(@JsonProperty("start") String start,
                               @JsonProperty("end") String end,
                               @JsonProperty("studentName") String studentName) {
        this.start = start;
        this.end = end;
        this.studentName = studentName;
    }

    /**
     * Constructs a {@code JsonAdaptedTimeslot} from a model {@code Timeslot}.
     */
    public JsonAdaptedTimeslot(Timeslot source) {
        this.start = source.getStart().format(Timeslot.FORMATTER);
        this.end = source.getEnd().format(Timeslot.FORMATTER);
        this.studentName = source.getStudentName(); // may be null
    }

    /**
     * Converts this Jackson-friendly adapted timeslot object into the model's {@code Timeslot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Timeslot toModelType() throws IllegalValueException {
        if (start == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start"));
        }
        if (end == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end"));
        }

        LocalDateTime startDt;
        LocalDateTime endDt;
        try {
            startDt = LocalDateTime.parse(start, Timeslot.FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid start datetime format: " + start);
        }
        try {
            endDt = LocalDateTime.parse(end, Timeslot.FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid end datetime format: " + end);
        }

        // If studentName present and non-empty, create a ConsultationTimeslot; otherwise a plain Timeslot.
        if (studentName != null && !studentName.isEmpty()) {
            return new ConsultationTimeslot(startDt, endDt, studentName);
        } else {
            return new Timeslot(startDt, endDt);
        }
    }
}
