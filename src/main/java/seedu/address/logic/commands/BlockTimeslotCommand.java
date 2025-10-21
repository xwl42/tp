package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.timeslot.Timeslot;

/**
 * Adds a timeslot to the model.
 */
public class BlockTimeslotCommand extends Command {

    public static final String COMMAND_WORD = "block-timeslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a timeslot.\n"
            + "Parameters: "
            + "ts/START_DATETIME te/END_DATETIME\n"
            + "Accepted datetime formats:\n"
            + " - ISO_LOCAL_DATE_TIME: 2023-10-01T09:00:00\n"
            + " - Human-friendly: d MMM uuuu, HH:mm (e.g. 1 Jan 2025, 10:00)\n"
            + "Example: " + COMMAND_WORD + " ts/2023-10-01T09:00:00 te/2023-10-01T10:00:00";

    public static final String MESSAGE_SUCCESS = "Added timeslot: %1$s";
    public static final String MESSAGE_DUPLICATE_TIMESLOT = "This timeslot already exists.";
    public static final String MESSAGE_INVALID_TIMESLOT = "Invalid timeslot (end must be after start).";

    private final Timeslot toAdd;

    /**
     * Creates a BlockTimeslotCommand that will add the specified {@code Timeslot}.
     *
     * @param timeslot the timeslot to add; must not be null.
     */
    public BlockTimeslotCommand(Timeslot timeslot) {
        requireNonNull(timeslot);
        this.toAdd = timeslot;
    }

    /**
     * Executes the block-timeslot command, adding the timeslot to the model.
     *
     * @param model the model to add the timeslot to.
     * @return the command result with feedback.
     * @throws CommandException if the model does not support timeslot modification or a duplicate exists.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!(model instanceof ModelManager)) {
            throw new CommandException("Model does not support timeslots modification.");
        }
        ModelManager mm = (ModelManager) model;
        if (mm.hasTimeslot(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TIMESLOT);
        }
        mm.addTimeslot(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BlockTimeslotCommand
                && toAdd.equals(((BlockTimeslotCommand) other).toAdd));
    }
}
