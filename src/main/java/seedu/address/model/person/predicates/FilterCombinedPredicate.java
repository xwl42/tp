package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 *  A {@code Predicate} over {@code Person} that AND-combines exercise and lab attendance predicates.
 */
public class FilterCombinedPredicate implements Predicate<Person> {

    private Predicate<Person> combinedPredicate;
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a predicate that matches a {@code Person} if any of the provided predicates match.
     *
     * @param predicates a non-empty list of individual field {@code Predicate}
     *                  targeting different {@code Person} fields.
     */
    public FilterCombinedPredicate(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
        combinedPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            combinedPredicate = combinedPredicate.and(predicates.get(i));
        }
    }

    public List<Predicate<Person>> getPredicates() {
        return List.copyOf(predicates);
    }

    @Override
    public boolean test(Person person) {
        return combinedPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCombinedPredicate)) {
            return false;
        }

        FilterCombinedPredicate otherPredicate = (FilterCombinedPredicate) other;

        if (predicates.size() != otherPredicate.predicates.size()) {
            return false;
        }

        for (int i = 0; i < predicates.size(); i++) {
            if (!predicates.get(i).equals(
                    otherPredicate.getPredicates().get(i))) {
                return false;
            }
        }
        return true;
    }


}
