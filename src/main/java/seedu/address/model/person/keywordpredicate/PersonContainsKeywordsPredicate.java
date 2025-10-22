package seedu.address.model.person.keywordpredicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 *  A {@code Predicate} over {@code Person} that OR-combines multiple field-level predicates.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {

    private Predicate<Person> combinedPredicate;
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a predicate that matches a {@code Person} if any of the provided predicates match.
     *
     * @param predicates a non-empty list of individual field {@code Predicate}
     *                  targeting different {@code Person} fields.
     */
    public PersonContainsKeywordsPredicate(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
        combinedPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            combinedPredicate = combinedPredicate.or(predicates.get(i));
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
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;

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
