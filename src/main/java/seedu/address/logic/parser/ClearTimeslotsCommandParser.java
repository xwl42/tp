package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearTimeslotsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearTimeslotsCommand object
 */
public class ClearTimeslotsCommandParser implements Parser<ClearTimeslotsCommand> {

    @Override
    public ClearTimeslotsCommand parse(String args) throws ParseException {
        String trimmed = args == null ? "" : args.trim();
        if (!trimmed.isEmpty()) {
            String msg = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClearTimeslotsCommand.MESSAGE_USAGE);
            throw new ParseException(msg);
        }
        return new ClearTimeslotsCommand();
    }
}
