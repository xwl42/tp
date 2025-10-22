package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.ExerciseTracker.NUMBER_OF_EXERCISES;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ExerciseTracker;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;


/**
 * Command used to mark an exercise
 */
public class MarkExerciseCommand extends Command {
    public static final String COMMAND_WORD = "marke";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the exercise status of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing exercise status will be updated based on the input.\n"
            + "Parameters: INDEX (must be a positive integer) ei/EXERCISEINDEX s/STATUS \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "ei/1 "
            + "s/d \n";
    public static final String MESSAGE_MARK_EXERCISE =
            "Exercise %d marked as %s for student %d (%s)";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS =
            "Index out of bounds! Index should be a number from 0 to %d";
    private static final int HIGHEST_INDEX = NUMBER_OF_EXERCISES - 1;
    private final Status status;
    private final Index studentIndex;
    private final Index exerciseIndex;

    /**
     * @param studentIndex of the student to be marked
     * @param exerciseIndex exercise number to mark
     * @param status status to mark the exercise with
     */
    public MarkExerciseCommand(Status status, Index studentIndex, Index exerciseIndex) {
        requireAllNonNull(studentIndex, status, exerciseIndex);
        this.studentIndex = studentIndex;
        this.exerciseIndex = exerciseIndex;
        this.status = status;
    }
    public Index getExerciseIndex() {
        return exerciseIndex;
    }

    public Index getStudentIndex() {
        return studentIndex;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person student = lastShownList.get(studentIndex.getZeroBased());
        assert student != null : "student should not be null";
        model.saveAddressBook();

        // Create a copy of the ExerciseTracker
        ExerciseTracker updatedExerciseTracker = student.getExerciseTracker().copy();

        try {
            updatedExerciseTracker.markExercise(exerciseIndex, status); // Modify the copy
        } catch (IndexOutOfBoundsException iob) {
            throw new CommandException(
                    String.format(MESSAGE_INDEX_OUT_OF_BOUNDS, HIGHEST_INDEX)
            );
        }

        // Create a NEW Person with the updated ExerciseTracker
        Person updatedStudent = new Person(
                student.getStudentId(),
                student.getName(),
                student.getPhone(),
                student.getEmail(),
                student.getTags(),
                student.getGithubUsername(),
                updatedExerciseTracker, // Use the modified copy
                student.getLabAttendanceList(),
                student.getGradeMap()
        );

        model.setPerson(student, updatedStudent);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_MARK_EXERCISE,
                exerciseIndex.getZeroBased(),
                status,
                studentIndex.getOneBased(),
                updatedStudent.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkExerciseCommand)) {
            return false;
        }

        MarkExerciseCommand e = (MarkExerciseCommand) other;
        return studentIndex.equals(e.getStudentIndex())
                && status.equals(e.getStatus())
                && exerciseIndex.equals(e.getExerciseIndex());
    }
}


