package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.index.MultiIndex;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.LabAttendanceList;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Person;

/**
 * Marks the specified lab as attended or not attended for one or more persons in the address book.
 */
public class MarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "marka";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the specific lab of the person(s) identified "
            + "by the index number(s) used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer or range X:Y) "
            + PREFIX_LAB_NUMBER + "LABNUMBER "
            + PREFIX_STATUS + "ATTENDANCESTATUS\n"
            + "Example: " + COMMAND_WORD + " 1:5 "
            + PREFIX_LAB_NUMBER + "1 " + PREFIX_STATUS + "y";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS =
            "Lab %1$d marked as attended for: %2$s";
    public static final String MESSAGE_MARK_ABSENCE_SUCCESS =
            "Lab %1$d marked as not attended for: %2$s";
    public static final String MESSAGE_FAILURE_ALREADY_ATTENDED = "Lab %1$d already marked as attended for %2$s";
    public static final String MESSAGE_FAILURE_ALREADY_NOT_ATTENDED =
            "Lab %1$d already marked as not attended for %2$s";
    public static final String MESSAGE_FAILURE_INVALID_LAB_INDEX = "The lab index provided is invalid";

    private final MultiIndex multiIndex;
    private final Index labNumber;
    private final boolean isAttended;
    private final StringBuilder exceptionMessageCompiler = new StringBuilder();

    /**
     * @param multiIndex range or single index of persons in the filtered list
     * @param labNumber index of the lab number to be marked
     * @param isAttended attendance status to set
     */
    public MarkAttendanceCommand(MultiIndex multiIndex, Index labNumber, boolean isAttended) {
        requireAllNonNull(multiIndex, labNumber);
        this.multiIndex = multiIndex;
        this.labNumber = labNumber;
        this.isAttended = isAttended;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> updatedPersons = new ArrayList<>();

        for (Index index : multiIndex.toIndexList()) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToEdit = lastShownList.get(index.getZeroBased());
            LabAttendanceList labAttendanceList = ((LabList) personToEdit.getLabAttendanceList()).copy();

            try {
                if (isAttended) {
                    labAttendanceList.markLabAsAttended(labNumber.getZeroBased());
                } else {
                    labAttendanceList.markLabAsAbsent(labNumber.getZeroBased());
                }

            } catch (IndexOutOfBoundsException iob) {
                throw new CommandException(MESSAGE_FAILURE_INVALID_LAB_INDEX);
            } catch (IllegalStateException ise) {
                String message;
                if (isAttended) {
                    message = String.format(MESSAGE_FAILURE_ALREADY_ATTENDED,
                            labNumber.getOneBased(), personToEdit.getName()) + "\n";
                } else {
                    message = String.format(MESSAGE_FAILURE_ALREADY_NOT_ATTENDED,
                            labNumber.getOneBased(), personToEdit.getName()) + "\n";
                }
                exceptionMessageCompiler.append(message);
                continue;
            }

            Person editedPerson = new Person(
                    personToEdit.getStudentId(), personToEdit.getName(), personToEdit.getPhone(),
                    personToEdit.getEmail(), personToEdit.getTags(),
                    personToEdit.getGithubUsername(), personToEdit.getExerciseTracker(),
                    labAttendanceList, personToEdit.getGradeMap());
            model.setPerson(personToEdit, editedPerson);
            updatedPersons.add(editedPerson);
        }

        model.saveAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateResponseMessage(updatedPersons, isAttended));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand otherCommand = (MarkAttendanceCommand) other;
        return multiIndex.equals(otherCommand.multiIndex)
                && labNumber.equals(otherCommand.labNumber)
                && isAttended == otherCommand.isAttended;
    }

    private String generateResponseMessage(List<Person> personsEdited, boolean isAttended) {
        String studentNames = personsEdited.stream()
                .map(person -> person.getName().fullName)
                .collect(java.util.stream.Collectors.joining(", "));
        if (personsEdited.isEmpty()) {
            return exceptionMessageCompiler.toString();
        }
        if (isAttended) {
            return exceptionMessageCompiler
                    + String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                    labNumber.getOneBased(), studentNames);
        } else {
            return exceptionMessageCompiler
                + String.format(MESSAGE_MARK_ABSENCE_SUCCESS,
                    labNumber.getOneBased(), studentNames);
        }
    }
}
