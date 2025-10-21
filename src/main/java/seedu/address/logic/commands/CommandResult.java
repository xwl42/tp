package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Optional payload: merged timeslot ranges (each element is a LocalDateTime[2] = {start, end}).
     * May be null if the command does not carry timeslot data.
     */
    private final List<LocalDateTime[]> timeslotRanges;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     *
     * @param feedbackToUser the feedback message to be shown to the user; must not be null.
     * @param showHelp whether help should be shown.
     * @param exit whether the application should exit.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}.
     * Other flags are set to their default values.
     *
     * @param feedbackToUser the feedback message to be shown to the user; must not be null.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified feedback and an optional
     * payload of merged timeslot ranges.
     *
     * @param feedbackToUser non-null feedback message to be displayed to the user.
     * @param timeslotRanges optional list of merged timeslot ranges; may be null.
     */
    public CommandResult(String feedbackToUser, List<LocalDateTime[]> timeslotRanges) {
        this(feedbackToUser, false, false, timeslotRanges);
    }

    /**
     * Constructs a {@code CommandResult} with all fields specified.
     *
     * @param feedbackToUser non-null feedback message to be shown to the user.
     * @param showHelp whether help should be shown.
     * @param exit whether the application should exit.
     * @param timeslotRanges optional list of merged timeslot ranges; may be null.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, List<LocalDateTime[]> timeslotRanges) {
        this.feedbackToUser = Objects.requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.timeslotRanges = timeslotRanges;
    }

    /**
     * Returns the feedback message to be shown to the user.
     *
     * @return the feedback message.
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns whether help should be shown to the user.
     *
     * @return true if help should be shown, false otherwise.
     */
    public boolean isShowHelp() {
        return showHelp;
    }

    /**
     * Returns whether the application should exit.
     *
     * @return true if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Returns the merged timeslot ranges payload, or null if not present.
     *
     * Each element of the returned list (if non-null) is a {@code LocalDateTime[]} of length 2
     * representing {start, end}.
     *
     * @return list of merged timeslot ranges or null.
     */
    public List<LocalDateTime[]> getTimeslotRanges() {
        return timeslotRanges;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && Objects.equals(timeslotRanges, otherCommandResult.timeslotRanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, timeslotRanges);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
