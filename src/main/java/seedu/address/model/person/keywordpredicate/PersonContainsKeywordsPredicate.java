package seedu.address.model.person.keywordpredicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

public class PersonContainsKeywordsPredicate implements Predicate<Person>{

    private Predicate<Person> combinedPredicate;
    private final List<Predicate<Person>> predicates;

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

        PersonContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (PersonContainsKeywordsPredicate) other;
        for (int i = 0; i < predicates.size(); i++) {
            if (!predicates.get(i).equals(
                    otherNameContainsKeywordsPredicate.getPredicates().get(i))) {
                return false;
            }
        }
        return true;
    }


}
