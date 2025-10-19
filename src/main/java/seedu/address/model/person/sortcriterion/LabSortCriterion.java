package seedu.address.model.person.sortcriterion;

import java.util.Comparator;

import seedu.address.model.person.Person;

public class LabSortCriterion extends SortCriterion {
    public static final String CRITERION_KEYWORD = "lab";

    @Override
    public Comparator<Person> getComparator() {
        return Comparator.comparing(Person::getLabAttendanceList, Comparator.reverseOrder());
    }

    @Override
    public String getDisplayString() {
        return "lab attendance";
    }

    @Override
    public String toString() {
        return CRITERION_KEYWORD;
    }
}
