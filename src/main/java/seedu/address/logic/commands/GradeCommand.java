package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.address.model.person.GradeMap.VALID_EXAM_NAMES;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.MultiIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.InvalidExamNameException;
import seedu.address.model.person.exceptions.InvalidScoreException;

/**
 * Grades the specified exam for one or more students in the address book.
 */
public class GradeCommand extends MultiIndexCommand {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Grades the specified exam for one or more students "
            + "identified by their index numbers in the displayed student list.\n"
            + "Parameters: INDEX... (must be positive integers) "
            + PREFIX_EXAM_NAME + "EXAM_NAME "
            + PREFIX_SCORE + "SCORE\n"
            + "Example: " + COMMAND_WORD + " 1:2 "
            + PREFIX_EXAM_NAME + "midterm "
            + PREFIX_SCORE + "30.5";

    public static final String MESSAGE_GRADE_SUCCESS = "%s graded with score %.1f for: %s";
    private static final String MESSAGE_FAILURE_INVALID_SCORE =
            "%.1f is invalid! Grade %s with a number in between 0 and %.1f (inclusive)";
    private static final String MESSAGE_FAILURE_INVALID_NAME =
            "%s is invalid! Here are the valid exam names %s";

    private final String examName;
    private final double score;

    /**
     * @param multiIndex list of students to grade
     * @param examName name of exam to grade
     * @param score score to assign
     */
    public GradeCommand(MultiIndex multiIndex, String examName, double score) {
        super(multiIndex);
        requireAllNonNull(multiIndex, examName, score);
        this.examName = examName;
        this.score = score;
    }

    @Override
    protected Person applyActionToPerson(Model model, Person personToGrade) throws CommandException {
        GradeMap updatedGradeMap = personToGrade.getGradeMap().copy();

        try {
            updatedGradeMap.gradeExam(examName, score);
            assert updatedGradeMap.getGradeableHashMap().get(examName) != null
                    : "Updated GradeMap should contain the graded exam";
        } catch (InvalidExamNameException iene) {
            throw new CommandException(String.format(
                    MESSAGE_FAILURE_INVALID_NAME,
                    examName,
                    Arrays.toString(VALID_EXAM_NAMES)
            ));
        } catch (InvalidScoreException ise) {
            throw new CommandException(String.format(
                    MESSAGE_FAILURE_INVALID_SCORE,
                    score,
                    examName,
                    ise.getMaxScore()
            ));
        }

        // Create a new Person with the updated GradeMap
        Person gradedPerson = new Person(
                personToGrade.getStudentId(),
                personToGrade.getName(),
                personToGrade.getPhone(),
                personToGrade.getEmail(),
                personToGrade.getTags(),
                personToGrade.getGithubUsername(),
                personToGrade.getExerciseTracker(),
                personToGrade.getLabAttendanceList(),
                updatedGradeMap
        );

        model.setPerson(personToGrade, gradedPerson);
        return gradedPerson;
    }

    @Override
    protected CommandResult buildResult(List<Person> updatedPersons) {
        String result = updatedPersons.stream()
                .map(p -> p.getName().toString())
                .collect(Collectors.joining(", "));
        return new CommandResult(String.format(
                MESSAGE_GRADE_SUCCESS,
                examName,
                score,
                result
        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GradeCommand)) {
            return false;
        }

        GradeCommand otherCommand = (GradeCommand) other;
        return multiIndex.equals(otherCommand.multiIndex)
                && score == otherCommand.score
                && examName.equals(otherCommand.examName);
    }
}
