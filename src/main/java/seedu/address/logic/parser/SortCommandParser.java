package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.sortcriterion.NameSortCriterion;

public class SortCommandParser implements Parser<SortCommand>{

    @Override
    public SortCommand parse(String userInput) throws ParseException {
        // Hard Coded as of now
        return new SortCommand(new NameSortCriterion());
    }
}
