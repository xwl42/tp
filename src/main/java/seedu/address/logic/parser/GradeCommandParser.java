package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import seedu.address.commons.core.index.MultiIndex;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.InvalidIndexException;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new GradeCommand object
 */
public class GradeCommandParser implements Parser<GradeCommand> {
    public static final String MESSAGE_INVALID_EXAM_NAME_FORMAT =
            "%s is an invalid exam name. Exam name must be one of %s";
    public static final String INVALID_SCORE_INPUT_FORMAT = "%s is not a number ";
    private static final String EMPTY_PREFIX_FORMAT = "Prefix %s : has empty value!";
    @Override
    public GradeCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EXAM_NAME, PREFIX_SCORE);
        MultiIndex studentIndex;
        String examName;
        String scoreString = "";
        double score;
        try {
            studentIndex = ParserUtil.parseMultiIndex(argumentMultimap.getPreamble());
        } catch (InvalidIndexException iie) {
            throw new ParseException("Student " + iie.getMessage());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradeCommand.MESSAGE_USAGE), pe);
        }
        try {
            examName = argumentMultimap.getValue(PREFIX_EXAM_NAME).orElseThrow(()
                    -> new ParseException(
                            String.format(EMPTY_PREFIX_FORMAT, PREFIX_EXAM_NAME)
                    )
            );
            scoreString = argumentMultimap.getValue(PREFIX_SCORE).orElseThrow(()
                    -> new ParseException(
                            String.format(EMPTY_PREFIX_FORMAT, PREFIX_SCORE)
                    )
            );
            score = Double.parseDouble(scoreString);
            score = Math.floor(score * 10) / 10.0; // Make it at most 1dp
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradeCommand.MESSAGE_USAGE), ive);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(INVALID_SCORE_INPUT_FORMAT, scoreString), e
            );
        }
        return new GradeCommand(studentIndex, examName, score);
    }
}
