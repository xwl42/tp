package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;

/**
 * Adds a consultation (timeslot associated with a student's name) to the model.
 */
public class AddConsultationCommand extends Command {

    public static final String COMMAND_WORD = "add-consultation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a consultation timeslot.\n"
            + "Parameters: ts/START_DATETIME te/END_DATETIME n/STUDENT_NAME\n"
            + "Accepted datetime formats: ISO_LOCAL_DATE_TIME or 'd MMM uuuu, HH:mm'\n"
            + "Example: " + COMMAND_WORD + " ts/2025-10-04T10:00:00\n"
            + " te/2025-10-04T11:00:00 n/John Doe";

    public static final String MESSAGE_SUCCESS = "Added consultation: %1$s with %2$s";
    public static final String MESSAGE_DUPLICATE_CONSULTATION =
                                "A consultation with the same time and student already exists.";
    public static final String MESSAGE_DUPLICATE_TIMESLOT = "A timeslot at the same time already exists.";

    private final ConsultationTimeslot toAdd;

    /**
     * Creates an AddConsultationCommand to add the specified consultation timeslot.
     *
     * @param consultationTimeslot ConsultationTimeslot to add; must not be null.
     */
    public AddConsultationCommand(ConsultationTimeslot consultationTimeslot) {
        requireNonNull(consultationTimeslot);
        this.toAdd = consultationTimeslot;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!(model instanceof ModelManager)) {
            throw new CommandException("Model does not support timeslots modification.");
        }
        ModelManager mm = (ModelManager) model;

        // Reject identical-interval duplicate consultation OR any overlapping timeslot.
        for (Timeslot existing : mm.getTimeslots().getTimeslotList()) {
            boolean sameInterval = existing.getStart().equals(toAdd.getStart())
                    && existing.getEnd().equals(toAdd.getEnd());
            if (sameInterval) {
                if (existing instanceof ConsultationTimeslot) {
                    ConsultationTimeslot c = (ConsultationTimeslot) existing;
                    if (toAdd.getStudentName().equals(c.getStudentName())) {
                        throw new CommandException(MESSAGE_DUPLICATE_CONSULTATION);
                    } else {
                        // Another consultation exists at same time with different student -> collision
                        throw new CommandException(MESSAGE_DUPLICATE_TIMESLOT);
                    }
                } else {
                    // Existing generic timeslot at same time -> cannot add consultation
                    throw new CommandException(MESSAGE_DUPLICATE_TIMESLOT);
                }
            }
            // General overlap check: deny any partial/full overlap with existing timeslot
            if (overlaps(existing, toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_TIMESLOT);
            }
        }

        // persist previous state for undo
        model.saveAddressBook();

        mm.addTimeslot(toAdd);

        String timeslotStr = String.format("%s -> %s",
                toAdd.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                toAdd.getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return new CommandResult(String.format(MESSAGE_SUCCESS, timeslotStr, toAdd.getStudentName()));
    }

    /**
     * Two timeslots overlap if their intervals intersect (end > start and start < end).
     */
    private static boolean overlaps(Timeslot a, Timeslot b) {
        return a.getEnd().isAfter(b.getStart()) && a.getStart().isBefore(b.getEnd());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddConsultationCommand)) {
            return false;
        }
        AddConsultationCommand o = (AddConsultationCommand) other;
        return Objects.equals(toAdd, o.toAdd);
    }
}
