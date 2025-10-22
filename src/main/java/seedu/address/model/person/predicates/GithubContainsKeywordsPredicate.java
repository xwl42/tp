package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;


/**
 * Tests that a {@code Person}'s {@code Github Username} matches any of the keywords given.
 */
public class GithubContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public GithubContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String github = person.getGithubUsername().toString().toLowerCase();
        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(github::contains);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GithubContainsKeywordsPredicate)) {
            return false;
        }

        GithubContainsKeywordsPredicate otherPredicate = (GithubContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
