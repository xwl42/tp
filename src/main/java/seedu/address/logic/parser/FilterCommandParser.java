package seedu.address.logic.parser;

import static java.lang.Boolean.TRUE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PrefixPredicateContainer;
import seedu.address.model.person.Status;
import seedu.address.model.person.predicates.ExerciseStatusMatchesPredicate;
import seedu.address.model.person.predicates.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_INDEX, PREFIX_LAB_NUMBER);

        Optional<String> exerciseIndexOptional = argMultimap.getValue(PREFIX_EXERCISE_INDEX);
        Optional<String> labNumberOptional = argMultimap.getValue(PREFIX_LAB_NUMBER);

//        List<Predicate<Person>> predicates = new ArrayList<>();

        if (exerciseIndexOptional.isPresent()) {
            String exerciseIndexStatus = exerciseIndexOptional.get();
            Pair<String, Status> indexStatusPair = ParserUtil.parseExerciseIndexStatus(exerciseIndexStatus);
            Status exerciseStatus = indexStatusPair.getValue();
            String exerciseIndexString = indexStatusPair.getKey();
            Index exerciseIndex = ParserUtil.parseZeroBasedIndex(exerciseIndexString);
//            predicates.add(new ExerciseStatusMatchesPredicate((exerciseIndex), exerciseStatus));
            return new FilterCommand(new ExerciseStatusMatchesPredicate((exerciseIndex), exerciseStatus));
        }

//        if (labNumber.isPresent()) {
//
//        }


        return new FilterCommand();
    }

}
