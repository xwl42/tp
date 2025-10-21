package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import seedu.address.logic.commands.BlockTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeslot.Timeslot;

/**
 * Parses input arguments and creates a new BlockTimeslotCommand object
 */
public class BlockTimeslotCommandParser implements Parser<BlockTimeslotCommand> {

    // Alternate human-friendly formats:
    // with comma: "1 Jan 2025, 10:00"
    private static final DateTimeFormatter ALTERNATE_WITH_COMMA =
            DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm");
    // without comma: "1 Jan 2025 10:00"
    private static final DateTimeFormatter ALTERNATE_NO_COMMA =
            DateTimeFormatter.ofPattern("d MMM uuuu HH:mm");

    private static LocalDateTime parseFlexibleDateTime(String input) throws DateTimeParseException {
        Objects.requireNonNull(input);
        String trimmed = input.trim();
        // try ISO first (used for JSON/standard)
        try {
            return LocalDateTime.parse(trimmed, Timeslot.FORMATTER);
        } catch (DateTimeParseException e) {
            // try human-friendly with comma
            try {
                return LocalDateTime.parse(trimmed, ALTERNATE_WITH_COMMA);
            } catch (DateTimeParseException ex2) {
                // try human-friendly without comma
                return LocalDateTime.parse(trimmed, ALTERNATE_NO_COMMA);
            }
        }
    }

    @Override
    public BlockTimeslotCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_TIMESLOT_START, CliSyntax.PREFIX_TIMESLOT_END);

        if (!argMultimap.getPreamble().isEmpty()
                || !argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_START).isPresent()
                || !argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_END).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockTimeslotCommand.MESSAGE_USAGE));
        }

        String startStr = argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_START).get();
        String endStr = argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_END).get();

        try {
            LocalDateTime start = parseFlexibleDateTime(startStr);
            LocalDateTime end = parseFlexibleDateTime(endStr);
            Timeslot ts = new Timeslot(start, end);
            return new BlockTimeslotCommand(ts);
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new ParseException("Invalid timeslot datetime or range. Accepted formats:\n"
                    + " - ISO: " + Timeslot.FORMATTER + " (e.g. 2023-10-01T09:00:00)\n"
                    + " - Human: d MMM uuuu, HH:mm or d MMM uuuu HH:mm (e.g. 4 Oct 2025, 10:00 or 4 Oct 2025 10:00)\n"
                    + "Ensure end is after start and prefixes are ts/ and te/. Example:\n"
                    + "  block-timeslot ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00");
        }
    }
}
