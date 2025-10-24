package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import seedu.address.logic.commands.UnblockTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeslot.Timeslot;

/**
 * Parses input arguments and creates a new UnblockTimeslotCommand object.
 */
public class UnblockTimeslotCommandParser implements Parser<UnblockTimeslotCommand> {

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

        // try ISO first (accepts strings like 2025-10-04T10:00:00)
        try {
            return LocalDateTime.parse(trimmed, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            // fall through
        }

        // try Timeslot's configured formatter (if different)
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
    public UnblockTimeslotCommand parse(String args) throws ParseException {
        String trimmed = args == null ? "" : args.trim();

        // Expect two markers: "ts/" and "te/". Extract text between them.
        int tsIndex = trimmed.indexOf("ts/");
        int teIndex = trimmed.indexOf("te/");

        if (tsIndex == -1 || teIndex == -1 || tsIndex > teIndex) {
            String msg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnblockTimeslotCommand.MESSAGE_USAGE);
            throw new ParseException(msg);
        }

        String startStr = trimmed.substring(tsIndex + 3, teIndex).trim();
        String endStr = trimmed.substring(teIndex + 3).trim();

        if (startStr.isEmpty() || endStr.isEmpty()) {
            String msg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnblockTimeslotCommand.MESSAGE_USAGE);
            throw new ParseException(msg);
        }

        // Parse using the flexible parser accepting ISO, Timeslot.FORMATTER, and human-friendly with/without comma.
        LocalDateTime start;
        LocalDateTime end;
        try {
            start = parseFlexibleDateTime(startStr);
            end = parseFlexibleDateTime(endStr);
        } catch (DateTimeException e) {
            throw new ParseException("Invalid timeslot datetime or range.\nAccepted formats:\n"
                    + " - ISO_LOCAL_DATE_TIME: 2023-10-01T09:00:00\n"
                    + " - Human-friendly: 4 Oct 2025, 10:00  OR  4 Oct 2025 10:00");
        }

        if (!end.isAfter(start)) {
            throw new ParseException("End datetime must be after start datetime.");
        }

        Timeslot timeslot = new Timeslot(start, end);
        return new UnblockTimeslotCommand(timeslot);
    }
}
