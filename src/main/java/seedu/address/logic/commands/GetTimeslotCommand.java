package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTimeslots;
import seedu.address.model.timeslot.Timeslot;
import seedu.address.storage.TimeslotsStorage;

/**
 * Command to obtain and provide merged timeslot ranges.
 */
public class GetTimeslotCommand extends Command {

    public static final String COMMAND_WORD = "get-timeslots";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays merged timeslot ranges from the timeslots storage.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Timeslot ranges (merged):%n%s";
    public static final String MESSAGE_MODEL_UNSUPPORTED =
            "Model does not support timeslots retrieval.";

    public GetTimeslotCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Model must be a ModelManager (to access timeslots API added to ModelManager)
        if (!(model instanceof ModelManager)) {
            throw new CommandException(MESSAGE_MODEL_UNSUPPORTED);
        }
        ReadOnlyTimeslots roTimeslots = ((ModelManager) model).getTimeslots();

        List<Timeslot> list = new ArrayList<>(roTimeslots.getTimeslotList());
        list.sort(Comparator.comparing(Timeslot::getStart));

        ArrayList<LocalDateTime[]> merged = mergeOverlappingTimeslots(list);

        // Build human-readable lines for feedback
        StringBuilder sb = new StringBuilder();
        for (LocalDateTime[] range : merged) {
            sb.append(String.format("%s -> %s%n",
                    range[0].format(Timeslot.FORMATTER),
                    range[1].format(Timeslot.FORMATTER)));
        }
        if (merged.isEmpty()) {
            sb.append("No timeslots found.");
        }

        // Return the CommandResult carrying the merged ranges payload; UI layer will handle display.
        return new CommandResult(String.format(MESSAGE_SUCCESS, sb.toString()), merged);
    }

    /**
     * Reads timeslots from the provided storage and returns a list sorted by start time (ascending).
     * If the storage file is missing, an empty list is returned.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public static List<Timeslot> readAndSortTimeslots(TimeslotsStorage storage) throws DataLoadingException {
        Objects.requireNonNull(storage);
        Optional<ReadOnlyTimeslots> optional = storage.readTimeslots();
        List<Timeslot> result = new ArrayList<>();
        if (optional.isPresent()) {
            ReadOnlyTimeslots ro = optional.get();
            result.addAll(ro.getTimeslotList());
        }
        result.sort(Comparator.comparing(Timeslot::getStart));
        return result;
    }

    /**
     * Reads timeslots from the provided storage using a specific file path and returns a list sorted by start time.
     * If the storage file is missing, an empty list is returned.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public static List<Timeslot> readAndSortTimeslots(TimeslotsStorage storage, java.nio.file.Path filePath)
            throws DataLoadingException {
        Objects.requireNonNull(storage);
        Objects.requireNonNull(filePath);
        Optional<ReadOnlyTimeslots> optional = storage.readTimeslots(filePath);
        List<Timeslot> result = new ArrayList<>();
        if (optional.isPresent()) {
            ReadOnlyTimeslots ro = optional.get();
            result.addAll(ro.getTimeslotList());
        }
        result.sort(Comparator.comparing(Timeslot::getStart));
        return result;
    }

    /**
     * Merges overlapping timeslots from a sorted list into continuous time ranges.
     * <p>Assumes the given list contains only {@link Timeslot} objects, sorted by start time.</p>
     *
     * @param sorted List of timeslots sorted by start time.
     * @return List of merged time ranges as {@code LocalDateTime[]} pairs: [start, end].
     */
    public static ArrayList<LocalDateTime[]> mergeOverlappingTimeslots(List<Timeslot> sorted) {
        ArrayList<LocalDateTime[]> merged = new ArrayList<>();
        int n = sorted == null ? 0 : sorted.size();

        for (int i = 0; i < n; i++) {
            Timeslot current = sorted.get(i);
            LocalDateTime start = current.getStart();
            LocalDateTime end = current.getEnd();

            // If current interval is already fully covered by last merged interval, skip.
            if (!merged.isEmpty()) {
                LocalDateTime lastEnd = merged.get(merged.size() - 1)[1];
                if (!lastEnd.isBefore(end)) { // lastEnd >= end
                    continue;
                }
            }

            // Extend the interval with overlapping timeslots
            for (int j = i + 1; j < n; j++) {
                Timeslot next = sorted.get(j);
                if (next.getStart().isAfter(end)) {
                    break; // no overlap
                }
                // overlap exists; extend end if needed
                if (next.getEnd().isAfter(end)) {
                    end = next.getEnd();
                }
            }

            merged.add(new LocalDateTime[]{start, end});
        }

        return merged;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof GetTimeslotCommand;
    }
}
