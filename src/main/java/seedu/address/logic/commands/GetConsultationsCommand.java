package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTimeslots;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;
import seedu.address.storage.TimeslotsStorage;

/**
 * Command to obtain and provide merged consultation timeslot ranges.
 */
public class GetConsultationsCommand extends Command {

    public static final String COMMAND_WORD = "get-consultations";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays merged consultation timeslot ranges from the timeslots storage.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Consultations:%n%s";
    public static final String MESSAGE_MODEL_UNSUPPORTED =
            "Model does not support timeslots retrieval.";

    public GetConsultationsCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!(model instanceof ModelManager)) {
            throw new CommandException(MESSAGE_MODEL_UNSUPPORTED);
        }
        ReadOnlyTimeslots roTimeslots = ((ModelManager) model).getTimeslots();

        // Filter only ConsultationTimeslot entries
        List<Timeslot> list = new ArrayList<>(roTimeslots.getTimeslotList().stream()
                .filter(t -> t instanceof ConsultationTimeslot)
                .collect(Collectors.toList()));
        list.sort(Comparator.comparing(Timeslot::getStart));

        ArrayList<LocalDateTime[]> merged = GetTimeslotCommand.mergeOverlappingTimeslots(list);

        // Build human-readable lines for feedback
        StringBuilder sb = new StringBuilder();
        for (LocalDateTime[] range : merged) {
            sb.append(String.format("%s -> %s%n",
                    range[0].format(Timeslot.DISPLAY_FORMATTER),
                    range[1].format(Timeslot.DISPLAY_FORMATTER)));
        }
        if (merged.isEmpty()) {
            sb.append("No consultations found.");
            return new CommandResult(sb.toString());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, sb.toString()), merged);
    }

    /**
     * Reads consultations from the provided storage and returns a list sorted by start time (ascending).
     * If the storage file is missing, an empty list is returned.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public static List<Timeslot> readAndSortConsultations(TimeslotsStorage storage) throws DataLoadingException {
        Objects.requireNonNull(storage);
        Optional<ReadOnlyTimeslots> optional = storage.readTimeslots();
        List<Timeslot> result = new ArrayList<>();
        if (optional.isPresent()) {
            ReadOnlyTimeslots ro = optional.get();
            result.addAll(ro.getTimeslotList().stream()
                    .filter(t -> t instanceof ConsultationTimeslot)
                    .collect(Collectors.toList()));
        }
        result.sort(Comparator.comparing(Timeslot::getStart));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof GetConsultationsCommand;
    }
}
