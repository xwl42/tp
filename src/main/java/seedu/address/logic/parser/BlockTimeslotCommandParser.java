package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.BlockTimeslotCommand;
import seedu.address.model.timeslot.Timeslot;

/**
 * Parses input arguments and creates a new BlockTimeslotCommand object
 */
public class BlockTimeslotCommandParser implements Parser<BlockTimeslotCommand> {

    // Alternate user-friendly formats:
    // with comma: "1 Jan 2025, 10:00"
    private static final DateTimeFormatter ALTERNATE_WITH_COMMA =
            DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm");
    // without comma: "1 Jan 2025 10:00"
    private static final DateTimeFormatter ALTERNATE_NO_COMMA =
            DateTimeFormatter.ofPattern("d MMM uuuu HH:mm");

    private static LocalDateTime parseFlexibleDateTime(String input) throws DateTimeParseException {
        Objects.requireNonNull(input);
        String trimmed = input.trim();

        // try ISO first (accepts strings like 2025-10-04T10:00:00)
        try {
            return LocalDateTime.parse(trimmed, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            // fall through to other formatters
        }

        // try Timeslot's configured formatter
        try {
            return LocalDateTime.parse(trimmed, Timeslot.FORMATTER);
        } catch (DateTimeParseException e) {
            // try user-friendly with comma
            try {
                return LocalDateTime.parse(trimmed, ALTERNATE_WITH_COMMA);
            } catch (DateTimeParseException ex2) {
                // try user-friendly without comma
                return LocalDateTime.parse(trimmed, ALTERNATE_NO_COMMA);
            }
        }
    }

    @Override
    public BlockTimeslotCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_TIMESLOT_START, CliSyntax.PREFIX_TIMESLOT_END);

        String startStr = null;
        String endStr = null;

        // Prefer values from the tokenizer if present
        if (argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_START).isPresent()
                && argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_END).isPresent()) {
            startStr = argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_START).get();
            endStr = argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_END).get();
        } else {
            // Fallback: try to extract "ts/<start> te/<end>" from raw args (allows spaces/commas inside)
            Pattern p = Pattern.compile("(?i)\\bts/(.+?)\\s+te/(.+)$");
            Matcher m = p.matcher(args.trim());
            if (m.find()) {
                startStr = m.group(1).trim();
                endStr = m.group(2).trim();
            }
        }

        if (startStr == null || endStr == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BlockTimeslotCommand.MESSAGE_USAGE));
        }

        try {
            LocalDateTime start = parseFlexibleDateTime(startStr);
            LocalDateTime end = parseFlexibleDateTime(endStr);
            Timeslot ts = new Timeslot(start, end);
            return new BlockTimeslotCommand(ts);
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new ParseException("Invalid timeslot datetime or range.\n  Accepted formats:\n"
                    + " - ISO: 2025-10-04T010:00:00\n"
                    + " - Human: 4 Oct 2025, 10:00 or 4 Oct 2025 10:00\n"
                    + "Ensure end is after start and prefixes are ts/ and te/. Example:\n"
                    + "  block-timeslot ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00");
        }
    }
}
