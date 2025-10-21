package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearTimeslotsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ClearTimeslotsCommandParserTest {

    private final ClearTimeslotsCommandParser parser = new ClearTimeslotsCommandParser();

    @Test
    public void parse_emptyArg_success() throws Exception {
        ClearTimeslotsCommand expected = new ClearTimeslotsCommand();
        assertEquals(expected, parser.parse(""));
        assertEquals(expected, parser.parse("   "));
    }

    @Test
    public void parse_nonEmptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("unexpected"));
    }
}
