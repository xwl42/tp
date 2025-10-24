package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.SetWeekCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Week;

public class SetWeekCommandParser implements Parser<SetWeekCommand> {

    @Override
    public SetWeekCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Week currentWeek;

        try {
            int currentWeekNumber = Integer.parseInt(args);
            currentWeek = new Week(currentWeekNumber);
        } catch (NumberFormatException nfe) {
            throw new ParseException("Not a valid week");
        }

        return new SetWeekCommand();
    }
}
