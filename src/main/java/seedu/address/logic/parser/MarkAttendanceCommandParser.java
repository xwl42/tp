package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.index.MultiIndex;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code MarkAttendanceCommand} object.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code MarkAttendanceCommand}
     * and returns a {@code MarkAttendanceCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LAB_NUMBER, PREFIX_STATUS);

        MultiIndex multiIndex;
        Index labNumber;
        boolean isAttended;

        try {
            multiIndex = ParserUtil.parseMultiIndex(argMultimap.getPreamble());
            labNumber = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LAB_NUMBER)
                    .orElseThrow(() -> new IllegalValueException("Missing lab number.")));
            isAttended = ParserUtil.parseLabStatus(argMultimap.getValue(PREFIX_STATUS)
                    .orElseThrow(() -> new IllegalValueException("Missing status.")));
        } catch (IllegalValueException | IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE), e);
        }

        return new MarkAttendanceCommand(multiIndex, labNumber, isAttended);
    }
}
