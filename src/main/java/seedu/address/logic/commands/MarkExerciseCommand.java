package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command used to mark an exercise
 */
public class MarkExerciseCommand extends Command {
    public static final String COMMAND_WORD = "marke";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the exercise status of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing exercise status will be updated based on the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 "
            + "ei/ [EXERCISE INDEX]\n"
            + "s/ [EXERCISE STATUS]\n";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "MarkExercise command not implemented yet";
    private Status status;
    private Index studentIndex;
    private int exerciseIndex;
    /**
     * @param studentIndex of the student to be marked
     * @param exerciseIndex exercise number to mark
     * @param status status to mark the exercise with
     */
    public MarkExerciseCommand(Status status, Index studentIndex, int exerciseIndex) {
        requireAllNonNull(studentIndex, status, exerciseIndex);
        this.studentIndex = studentIndex;
        this.exerciseIndex = exerciseIndex;
        this.status = status;
    }
    public int getExerciseIndex() {
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
        System.out.println(status);
        System.out.println(studentIndex);
        System.out.println(exerciseIndex);
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
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
                && exerciseIndex == e.getExerciseIndex();
    }
}


