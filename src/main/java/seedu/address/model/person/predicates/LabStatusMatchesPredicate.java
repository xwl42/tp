package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.LabAttendance;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;


/**
 * Tests that a {@code Person}'s {@code Exercise status} matches the status of the exercise stated.
 */
public class LabStatusMatchesPredicate implements Predicate<Person> {
    private boolean status;
    private Index index;

    public LabStatusMatchesPredicate (Index index, boolean status) {
        this.index = index;
        this.status = status;
    }

    @Override
    public boolean test(Person person) {
        LabList labAttendanceList = (LabList) person.getLabAttendanceList();
        LabAttendance[] labAttendances = labAttendanceList.getLabs();
        int number = index.getZeroBased();
        return labAttendances[number].isAttended() == status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseStatusMatchesPredicate)) {
            return false;
        }

        LabStatusMatchesPredicate otherPredicate = (LabStatusMatchesPredicate) other;
        return status == (otherPredicate.status)
                && index.equals(otherPredicate.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Status", status)
                .add("index", index)
                .toString();
    }
}
