package seedu.address.model.person.keywordpredicate;

import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

public class PersonContainsKeywordsPredicate {

    private final List<Predicate<Person>> predicates;

    public PersonContainsKeywordsPredicate(List<String> keywords,) {

    }
}
