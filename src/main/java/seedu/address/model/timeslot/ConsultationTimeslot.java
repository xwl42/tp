package seedu.address.model.timeslot;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Timeslot that also carries a student's name (a consultation appointment).
 * Extends the base Timeslot so it can be stored in existing Timeslots collections.
 */
public class ConsultationTimeslot extends Timeslot {

    private final String studentName;

    /**
     * Jackson-friendly constructor and main constructor.
     *
     * @param start       start datetime (non-null)
     * @param end         end datetime (non-null)
     * @param studentName student name (may be empty)
     */
    @JsonCreator
    public ConsultationTimeslot(@JsonProperty("start") LocalDateTime start,
                                @JsonProperty("end") LocalDateTime end,
                                @JsonProperty("studentName") String studentName) {
        super(start, end);
        this.studentName = studentName == null ? "" : studentName;
    }

    /**
     * Convenience constructor without student name.
     */
    public ConsultationTimeslot(LocalDateTime start, LocalDateTime end) {
        this(start, end, "");
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        // Include student name when present
        if (studentName == null || studentName.isEmpty()) {
            return super.toString();
        }
        return String.format("%s (Consultation with %s)", super.toString(), studentName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ConsultationTimeslot)) {
            return false;
        }
        ConsultationTimeslot o = (ConsultationTimeslot) other;
        // Equality considers time range and student name
        return super.equals(o) && Objects.equals(studentName, o.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentName);
    }
}
