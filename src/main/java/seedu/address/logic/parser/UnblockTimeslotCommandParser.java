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

        // Try parsing supported formats: ISO first, then human-friendly.
        LocalDateTime start;
        LocalDateTime end;
        try {
            try {
                start = LocalDateTime.parse(startStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeException e1) {
                start = LocalDateTime.parse(startStr, DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm"));
            }

            try {
                end = LocalDateTime.parse(endStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeException e2) {
                end = LocalDateTime.parse(endStr, DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm"));
            }
        } catch (DateTimeException e) {
            throw new ParseException("Invalid timeslot datetime or range.\nAccepted formats:\n"
                    + " - ISO_LOCAL_DATE_TIME: 2023-10-01T09:00:00\n"
                    + " - Human-friendly: 4 Oct 2025, 10:00");
        }

        if (!end.isAfter(start)) {
            throw new ParseException("End datetime must be after start datetime.");
        }

        Timeslot timeslot = new Timeslot(start, end);
        return new UnblockTimeslotCommand(timeslot);
    }
}
