package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeslot.Timeslot;

/**
 * Removes a timeslot from the stored timeslots.
 */
public class UnblockTimeslotCommand extends Command {

    public static final String COMMAND_WORD = "unblock-timeslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a previously blocked timeslot.\n"
            + "Parameters: ts/START_DATETIME te/END_DATETIME\n"
            + "Accepted datetime formats:\n"
            + " - ISO_LOCAL_DATE_TIME: 2023-10-01T09:00:00\n"
            + " - Human-friendly: 4 Oct 2025, 10:00\n"
            + "Example: " + COMMAND_WORD + " ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00";

    public static final String MESSAGE_REMOVE_TIMESLOT_SUCCESS = "Removed timeslot: %1$s -> %2$s";
    public static final String MESSAGE_TIMESLOT_NOT_FOUND = "Timeslot not found: %1$s -> %2$s";

    private final Timeslot timeslot;

    public UnblockTimeslotCommand(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Objects.requireNonNull(timeslot);

        if (!model.hasTimeslot(timeslot)) {
            String start = timeslot.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String end = timeslot.getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            throw new CommandException(String.format(MESSAGE_TIMESLOT_NOT_FOUND, start, end));
        }

        model.removeTimeslot(timeslot);
        // persist handled by existing storage flow (Model/LogicManager)
        String start = timeslot.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String end = timeslot.getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return new CommandResult(String.format(MESSAGE_REMOVE_TIMESLOT_SUCCESS, start, end));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnblockTimeslotCommand)) {
            return false;
        }
        UnblockTimeslotCommand otherCmd = (UnblockTimeslotCommand) other;
        return timeslot.equals(otherCmd.timeslot);
    }
}
