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

/**
 * Marks the specified exam as passed ("y") or failed ("n")
 * for one or more students in the address book.
 */
public class GradeCommand extends MultiIndexCommand {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the specified exam as passed or failed "
            + "for one or more students identified by their index numbers in the displayed student list.\n"
            + "Parameters: INDEX... (must be positive integers) "
            + PREFIX_EXAM_NAME + "EXAM_NAME "
            + PREFIX_SCORE + "RESULT (y/n)\n"
            + "Example: " + COMMAND_WORD + " 1:3 " + PREFIX_EXAM_NAME + "midterm " + PREFIX_SCORE + "y\n"
            + "Example: " + COMMAND_WORD + " 2 " + PREFIX_EXAM_NAME + "pe1 " + PREFIX_SCORE + "n\n";

    public static final String MESSAGE_GRADE_SUCCESS = "%s marked as %s for: %s";
    public static final String MESSAGE_FAILURE_INVALID_NAME =
            "%s is invalid! Here are the valid exam names: %s";
    public static final String MESSAGE_FAILURE_INVALID_RESULT =
            "Invalid result! Use 'y' for passed or 'n' for failed.";

    private final String examName;
    private final boolean isPassed;

    /**
     * @param multiIndex list of students to update
     * @param examName name of exam
     * @param result result character ("y" or "n")
     */
    public GradeCommand(MultiIndex multiIndex, String examName, String result) {
        super(multiIndex);
        requireAllNonNull(multiIndex, examName, result);

        this.examName = examName.toLowerCase();

        String trimmed = result.trim().toLowerCase();

        if (trimmed.equals("y")) {
            this.isPassed = true;
        } else if (trimmed.equals("n")) {
            this.isPassed = false;
        } else {
            throw new IllegalArgumentException(MESSAGE_FAILURE_INVALID_RESULT);
        }
    }

    @Override
    protected Person applyActionToPerson(Model model, Person personToGrade) throws CommandException {
        GradeMap updatedGradeMap = personToGrade.getGradeMap().copy();

        try {
            if (isPassed) {
                updatedGradeMap.markExamPassed(examName);
            } else {
                updatedGradeMap.markExamFailed(examName);
            }

            if (updatedGradeMap.getExamMap().get(examName) == null) {
                throw new AssertionError("Updated GradeMap should contain the graded exam");
            }
        } catch (InvalidExamNameException e) {
            throw new CommandException(String.format(
                    MESSAGE_FAILURE_INVALID_NAME,
                    examName,
                    Arrays.toString(VALID_EXAM_NAMES)
            ));
        }

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
        String affectedStudents = updatedPersons.stream()
                .map(Person::getNameAndID)
                .collect(Collectors.joining(", "));

        String resultString = isPassed ? "Passed" : "Failed";

        return new CommandResult(String.format(
                MESSAGE_GRADE_SUCCESS,
                examName,
                resultString,
                affectedStudents
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

        if (!multiIndex.equals(otherCommand.multiIndex)) {
            return false;
        }

        if (isPassed != otherCommand.isPassed) {
            return false;
        }

        return examName.equals(otherCommand.examName);
    }
}
