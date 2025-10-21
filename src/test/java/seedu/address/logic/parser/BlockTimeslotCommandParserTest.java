package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BlockTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeslot.Timeslot;

public class BlockTimeslotCommandParserTest {

    private final BlockTimeslotCommandParser parser = new BlockTimeslotCommandParser();

    @Test
    public void parse_isoFormat_success() throws Exception {
        String args = "ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00";
        BlockTimeslotCommand cmd = parser.parse(args);

        Timeslot expected = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        assertEquals(new BlockTimeslotCommand(expected), cmd);
    }

    @Test
    public void parse_humanFormatWithComma_success() throws Exception {
        String args = "ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00";
        BlockTimeslotCommand cmd = parser.parse(args);

        Timeslot expected = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        assertEquals(new BlockTimeslotCommand(expected), cmd);
    }

    @Test
    public void parse_humanFormatNoComma_success() throws Exception {
        String args = "ts/4 Oct 2025 10:00 te/4 Oct 2025 13:00";
        BlockTimeslotCommand cmd = parser.parse(args);

        Timeslot expected = new Timeslot(LocalDateTime.of(2025, 10, 4, 10, 0),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        assertEquals(new BlockTimeslotCommand(expected), cmd);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String args = "ts/invalid te/alsoinvalid";
        assertThrows(ParseException.class, () -> parser.parse(args));
    }
}
