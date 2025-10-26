package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.index.MultiIndex;
import seedu.address.logic.commands.MarkExerciseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkExerciseCommandParserTest {

    private MarkExerciseCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new MarkExerciseCommandParser();
    }

    /**
     * Valid command should parse successfully.
     * Example: "1 ei/0 s/DONE"
     */
    @Test
    public void parse_validArgs_returnsMarkExerciseCommand() throws Exception {
        String input = "1 " + PREFIX_EXERCISE_INDEX + "0 " + PREFIX_STATUS + "y";
        MarkExerciseCommand expected = new MarkExerciseCommand(
                new MultiIndex(Index.fromZeroBased(0)), Index.fromOneBased(1), true);
        MarkExerciseCommand result = parser.parse(input);
        assertEquals(expected, result);
    }

    /**
     * Missing status prefix should throw ParseException.
     */
    @Test
    public void parse_missingStatusPrefix_throwsParseException() {
        String input = "marke 1" + PREFIX_EXERCISE_INDEX + "0";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(input));
    }

    /**
     * Missing exercise index prefix should throw ParseException.
     */
    @Test
    public void parse_missingExerciseIndexPrefix_throwsParseException() {
        String input = "1 " + PREFIX_STATUS + "DONE";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkExerciseCommand.MESSAGE_USAGE), e.getMessage());
    }

    /**
     * Invalid (non-integer) person index should throw ParseException.
     */
    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        String input = "abc " + PREFIX_EXERCISE_INDEX + "1 " + PREFIX_STATUS + "DONE";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    /**
     * Invalid (negative) exercise index should throw ParseException.
     */
    @Test
    public void parse_negativeExerciseIndex_throwsParseException() {
        String input = "1 " + PREFIX_EXERCISE_INDEX + "-2 " + PREFIX_STATUS + "DONE";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    /**
     * Invalid status value should throw ParseException.
     */
    @Test
    public void parse_invalidStatus_throwsParseException() {
        String input = "1 " + PREFIX_EXERCISE_INDEX + "0 " + PREFIX_STATUS + "INVALIDSTATUS";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(
                "Status must be y or n",
                e.getMessage()
        );
    }

    /**
     * Empty prefix value (e.g. "s/") should throw ParseException.
     */
    @Test
    public void parse_emptyPrefixValue_throwsParseException() {
        String input = "1 " + PREFIX_EXERCISE_INDEX + "0 " + PREFIX_STATUS + "";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
