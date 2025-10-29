package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.InvalidIndexException;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.helpers.Comparison;
import seedu.address.logic.helpers.ExerciseIndexStatus;
import seedu.address.logic.helpers.LabAttendanceComparison;
import seedu.address.logic.helpers.LabIndexStatus;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.model.person.predicates.ExerciseStatusMatchesPredicate;
import seedu.address.model.person.predicates.FilterCombinedPredicate;
import seedu.address.model.person.predicates.LabAttendanceMatchesPredicate;
import seedu.address.model.person.predicates.LabStatusMatchesPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    private static final Prefix[] FILTER_PREFIXES = { PREFIX_EXERCISE_INDEX, PREFIX_LAB_NUMBER, PREFIX_LAB_ATTENDANCE };

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, FILTER_PREFIXES);

        checkNoKeywords(argMultimap);
        List<Predicate<Person>> predicates = getSelectedPredicates(argMultimap);

        return new FilterCommand(new FilterCombinedPredicate(predicates));
    }

    private List<Predicate<Person>> getSelectedPredicates(ArgumentMultimap argMultimap) throws ParseException {
        List<String> exerciseIndexes = argMultimap.getAllValues(PREFIX_EXERCISE_INDEX);
        List<String> labNumbers = argMultimap.getAllValues(PREFIX_LAB_NUMBER);
        List<String> labAttendances = argMultimap.getAllValues(PREFIX_LAB_ATTENDANCE);

        List<Predicate<Person>> predicates = new ArrayList<>();

        for (String exerciseIndexStatus : exerciseIndexes) {
            if (exerciseIndexStatus.isBlank()) {
                throw new ParseException("Exercise value missing");
            }
            predicates.add(getExercisePredicate(exerciseIndexStatus));
        }

        for (String labNumberStatus : labNumbers) {
            if (labNumberStatus.isBlank()) {
                throw new ParseException("Lab value missing");
            }
            predicates.add(getLabPredicate(labNumberStatus));
        }

        if (!labAttendances.isEmpty()) {
            if (labAttendances.size() != 1) {
                throw new ParseException("Only 1 la/ allowed");
            }
            String labAttendance = labAttendances.get(0);
            if (labAttendance.isBlank()) {
                throw new ParseException("Lab attendance value missing");
            }
            predicates.add(getLabAttendancePredicate(labAttendance));
        }

        if (predicates.isEmpty()) {
            throw new ParseException(FilterCommand.MESSAGE_USAGE);
        }
        return predicates;
    }

    private ExerciseStatusMatchesPredicate getExercisePredicate(
            String exerciseIndexStatus) throws ParseException {
        ExerciseIndexStatus indexStatusPair = ParserUtil.parseExerciseIndexStatus(exerciseIndexStatus);

        Status exerciseStatus = indexStatusPair.getStatus();
        String exerciseIndexString = indexStatusPair.getExerciseIndex();
        Index exerciseIndex = ParserUtil.parseExerciseIndex(exerciseIndexString);

        return new ExerciseStatusMatchesPredicate(exerciseIndex, exerciseStatus);
    }

    private LabStatusMatchesPredicate getLabPredicate(
            String labNumberStatus) throws ParseException {
        LabIndexStatus labNumberStatusPair = ParserUtil.parseLabNumberStatus(labNumberStatus);

        String labStatus = labNumberStatusPair.getStatus();
        String labIndexString = labNumberStatusPair.getLabIndex();
        Index labIndex;

        try {
            labIndex = ParserUtil.parseIndex(labIndexString);
        } catch (InvalidIndexException iie) {
            throw new ParseException(MarkAttendanceCommand.MESSAGE_FAILURE_INVALID_LAB_INDEX);
        }

        return new LabStatusMatchesPredicate(labIndex, labStatus);
    }

    private LabAttendanceMatchesPredicate getLabAttendancePredicate(
            String labAttendenceStatus) throws ParseException {
        LabAttendanceComparison labAttendanceComparison = ParserUtil
                .parseAttendanceComparison(labAttendenceStatus);

        double value = labAttendanceComparison.getAttendance();
        Comparison comparison = labAttendanceComparison.getComparison();
        return new LabAttendanceMatchesPredicate(value, comparison);
    }

    private void checkNoKeywords(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().isBlank()) {
            throw new ParseException(FilterCommand.MESSAGE_USAGE);
        }
    }

}
