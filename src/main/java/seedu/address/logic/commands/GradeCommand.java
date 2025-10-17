package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.GradeMap.VALID_EXAM_NAMES;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.InvalidExamNameException;
import seedu.address.model.person.exceptions.InvalidScoreException;

/**
 * Marks the specified lab as attended of an existing person in the address book.
 */
public class GradeCommand extends Command {
    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": grades the specific exam of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_EXAM_NAME + " EXAM_NAME "
            + PREFIX_SCORE + " SCORE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXAM_NAME + "midterm "
            + PREFIX_SCORE + "30.5";
    private static final String MESSAGE_GRADE_SUCCESS = "%s of %s graded with score %.1f";
    private static final String MESSAGE_FAILURE_INVALID_SCORE =
            "%.1f is invalid! Grade the exam with a number in between 0 and %.1f (inclusive)";
    private static final String MESSAGE_FAILURE_INVALID_NAME =
            "%s is invalid! Here are the valid exam names %s";
    private final Index index;
    private final String examName;
    private final double score;

    /**
     * @param index of the person in the filtered person list to mark their lab attendance
     * @param index of the lab number to be marked as attended
     */
    public GradeCommand(Index index, String examName, double score) {
        requireAllNonNull(index, examName, score);
        this.index = index;
        this.examName = examName;
        this.score = score;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToGrade = lastShownList.get(index.getZeroBased());
        GradeMap gradeMap = personToGrade.getGradeMap();
        try {
            gradeMap.gradeExam(examName, score);
        } catch (InvalidExamNameException iene) {
            throw new CommandException(
                    String.format(
                            MESSAGE_FAILURE_INVALID_NAME,
                            examName,
                            Arrays.toString(VALID_EXAM_NAMES)
                    )
            );
        } catch (InvalidScoreException ise) {
            throw new CommandException(
                    String.format(MESSAGE_FAILURE_INVALID_SCORE,
                            score,
                            ise.getMaxScore()
                    )
            );
        }
        model.setPerson(personToGrade, personToGrade);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_GRADE_SUCCESS,
                examName,
                personToGrade.getName(),
                score
                )
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradeCommand)) {
            return false;
        }

        GradeCommand otherCommand = (GradeCommand) other;
        return index.equals(otherCommand.index)
                && score == (otherCommand.score)
                && examName.equals(otherCommand.examName);
    }
}
