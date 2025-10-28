package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.InvalidIndexException;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.model.person.predicates.ExerciseStatusMatchesPredicate;
import seedu.address.model.person.predicates.FilterCombinedPredicate;
import seedu.address.model.person.predicates.LabStatusMatchesPredicate;

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

        List<Predicate<Person>> predicates = new ArrayList<>();

        if (exerciseIndexOptional.isPresent()) {
            predicates.add(createExerciseFilter(exerciseIndexOptional));
        }

        if (labNumberOptional.isPresent()) {
            predicates.add(createLabFilter(labNumberOptional));
        }

        if (predicates.isEmpty()) {
            throw new ParseException(FilterCommand.MESSAGE_USAGE);
        }

        return new FilterCommand(new FilterCombinedPredicate(predicates));
    }

    private ExerciseStatusMatchesPredicate createExerciseFilter(
            Optional<String> exerciseIndexOptional) throws ParseException {
        assert(exerciseIndexOptional.isPresent());
        String exerciseIndexStatus = exerciseIndexOptional.get();
        Pair<String, Status> indexStatusPair = ParserUtil.parseExerciseIndexStatus(exerciseIndexStatus);
        Status exerciseStatus = indexStatusPair.getValue();
        String exerciseIndexString = indexStatusPair.getKey();
        Index exerciseIndex = ParserUtil.parseExerciseIndex(exerciseIndexString);
        return new ExerciseStatusMatchesPredicate(exerciseIndex, exerciseStatus);
    }

    private LabStatusMatchesPredicate createLabFilter(
            Optional<String> labNumberOptional) throws ParseException {
        assert(labNumberOptional.isPresent());
        String labNumberStatus = labNumberOptional.get();
        Pair<String, Boolean> labNumberStatusPair = ParserUtil.parseLabNumberStatus(labNumberStatus);
        Boolean labStatus = labNumberStatusPair.getValue();
        String labNumberString = labNumberStatusPair.getKey();
        Index labNumber;
        try {
            labNumber = ParserUtil.parseIndex(labNumberString);
        } catch (InvalidIndexException iie) {
            throw new ParseException(MarkAttendanceCommand.MESSAGE_FAILURE_INVALID_LAB_INDEX);
        }
        return new LabStatusMatchesPredicate(labNumber, labStatus);
    }

}
