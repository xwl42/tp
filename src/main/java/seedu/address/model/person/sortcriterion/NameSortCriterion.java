package seedu.address.model.person.sortcriterion;

import java.util.Comparator;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class NameSortCriterion extends SortCriterion{
    public static final String CRITERION_KEYWORD = "name";

    @Override
    public Comparator<Person> getComparator() {
        return (person1, person2) -> {
            Name name1 = person1.getName();
            Name name2 = person2.getName();
            return name1.compareTo(name2);
        };
    }

    @Override
    public String getDisplayString() {
        return "name";
    }

    @Override
    public String getCriterionKeyword() {
        return CRITERION_KEYWORD;
    }
}
