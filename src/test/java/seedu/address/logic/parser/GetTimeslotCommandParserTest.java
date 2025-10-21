package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class GetTimeslotCommandParserTest {

    private final GetTimeslotCommandParser parser = new GetTimeslotCommandParser();

    @Test
    public void parse_emptyArg_success() throws Exception {
        GetTimeslotCommand expected = new GetTimeslotCommand();
        assertEquals(expected, parser.parse("")); // empty arguments
        assertEquals(expected, parser.parse("   ")); // whitespace only
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("unexpectedArg"));
    }
}
