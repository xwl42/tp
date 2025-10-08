package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MarkExerciseCommand;
import seedu.address.logic.commands.Status;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser of the mark exercise command
 */
public class MarkExerciseCommandParser implements Parser<MarkExerciseCommand> {
    /**
     * Parse the user input into a mark exercise command
     * @param args user input
     * @return MarkExerciseCommand
     * @throws ParseException if user input is invalid
     */
    public MarkExerciseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_EXERCISE_INDEX, PREFIX_STATUS);
        String statusString = argMultimap.getValue(PREFIX_STATUS).orElse(null);
        Status status = null;
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            status = Status.valueOf(statusString.toUpperCase());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkExerciseCommand.MESSAGE_USAGE), ive);
        }

        int exerciseIndex = Integer.parseInt(argMultimap.getValue(PREFIX_EXERCISE_INDEX).orElse("-1"));

        return new MarkExerciseCommand(status, index, exerciseIndex);
    }

}

