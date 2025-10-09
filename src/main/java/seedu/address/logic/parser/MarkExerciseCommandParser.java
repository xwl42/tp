package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MarkExerciseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_INDEX, PREFIX_STATUS);

        Index personIndex;
        int exerciseIndex;
        Status status;

        // Parse the person index (from the command preamble)
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkExerciseCommand.MESSAGE_USAGE), ive);
        }

        // Parse the exercise index
        String exIndexString = argMultimap.getValue(PREFIX_EXERCISE_INDEX)
                .orElseThrow(() -> new ParseException("Missing exercise index prefix ex/"));
        try {
            exerciseIndex = Integer.parseInt(exIndexString);
        } catch (NumberFormatException e) {
            System.out.println(exIndexString);
            throw new ParseException("Exercise index must be a number.");
        }

        // Parse the status string and convert to enum
        String statusString = argMultimap.getValue(PREFIX_STATUS)
                .orElseThrow(() -> new ParseException("Missing status prefix s/"));
        try {
            status = Status.fromString(statusString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid status. Must be one of: "
                    + Arrays.toString(Status.values()));
        }

        // Return new command
        return new MarkExerciseCommand(status, personIndex, exerciseIndex);
    }
}

