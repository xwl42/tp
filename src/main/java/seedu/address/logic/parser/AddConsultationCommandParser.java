package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.AddConsultationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeslot.ConsultationTimeslot;

/**
 * Parses input arguments and creates a new AddConsultationCommand object.
 *
 * Expected format: ts/START_DATETIME te/END_DATETIME n/STUDENT_NAME
 */
public class AddConsultationCommandParser implements Parser<AddConsultationCommand> {

    @Override
    public AddConsultationCommand parse(String args) throws ParseException {
        // include the name prefix so "n/..." is parsed separately (otherwise it becomes part of end datetime)
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_TIMESLOT_START, CliSyntax.PREFIX_TIMESLOT_END, CliSyntax.PREFIX_NAME);

        // disallow duplicated ts/ or te/ prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_TIMESLOT_START, CliSyntax.PREFIX_TIMESLOT_END);

        // no free-form preamble allowed
        if (!argMultimap.getPreamble().isEmpty()) {
            String msg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddConsultationCommand.MESSAGE_USAGE);
            throw new ParseException(msg);
        }

        // use shared CliSyntax constants when reading values
        String startStr = argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_START).orElse("");
        String endStr = argMultimap.getValue(CliSyntax.PREFIX_TIMESLOT_END).orElse("");
        String studentName = argMultimap.getValue(CliSyntax.PREFIX_NAME).orElse("").trim();

        if (startStr.isEmpty() || endStr.isEmpty() || studentName.isEmpty()) {
            String msg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddConsultationCommand.MESSAGE_USAGE);
            throw new ParseException(msg);
        }

        LocalDateTime start = parseDateTime(startStr);
        LocalDateTime end = parseDateTime(endStr);

        if (!end.isAfter(start)) {
            throw new ParseException("End datetime must be after start datetime.");
        }

        ConsultationTimeslot consultation = new ConsultationTimeslot(start, end, studentName);
        return new AddConsultationCommand(consultation);
    }

    /**
     * Try a few accepted datetime formats. Throws ParseException if none match.
     */
    private LocalDateTime parseDateTime(String input) throws ParseException {
        String trimmed = input.trim();
        DateTimeFormatter[] fmts = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm"),
            DateTimeFormatter.ofPattern("d MMM uuuu HH:mm")
        };
        for (DateTimeFormatter fmt : fmts) {
            try {
                return LocalDateTime.parse(trimmed, fmt);
            } catch (DateTimeParseException e) {
                // try next
            }
        }
        throw new ParseException("Invalid datetime format: " + input);
    }
}
