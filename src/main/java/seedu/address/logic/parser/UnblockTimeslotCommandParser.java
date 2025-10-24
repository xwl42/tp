package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.UnblockTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeslot.Timeslot;

/**
 * Parses input arguments and creates a new UnblockTimeslotCommand object.
 */
public class UnblockTimeslotCommandParser implements Parser<UnblockTimeslotCommand> {

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

        // Try parsing using supported formats: ISO first, then human-friendly "d MMM uuuu, HH:mm" and "d MMM uuuu HH:mm"
        LocalDateTime start;
        LocalDateTime end;
        try {
            start = parseDateFlexible(startStr);
            end = parseDateFlexible(endStr);
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

    /**
     * Parse a datetime string accepting multiple formats:
     *  - ISO_LOCAL_DATE_TIME
     *  - "d MMM uuuu, HH:mm"
     *  - "d MMM uuuu HH:mm" (without comma)
     *
     * Throws DateTimeException if none match.
     */
    private static LocalDateTime parseDateFlexible(String input) throws DateTimeException {
        // try ISO first
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeException ignored) {
        }

        // then try human-friendly patterns (with and without comma)
        DateTimeFormatter[] humanFormats = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm"),
                DateTimeFormatter.ofPattern("d MMM uuuu HH:mm")
        };

        for (DateTimeFormatter fmt : humanFormats) {
            try {
                return LocalDateTime.parse(input, fmt);
            } catch (DateTimeException ignored) {
                // try next
            }
        }

        // none matched
        throw new DateTimeException("Unparseable datetime: " + input);
    }
}
