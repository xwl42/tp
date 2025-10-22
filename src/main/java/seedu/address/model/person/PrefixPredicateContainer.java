package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.GithubContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicates.StudentIdContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;


/**
 * Binds a {@link Prefix} (e.g., {@code n/}, {@code e/}) to a predicate “builder” for a {@link Person}
 * and tracks whether that field has been selected as a search target.
 */
public class PrefixPredicateContainer {
    private Prefix prefix;
    private PredicateWrapper predicateWrapper;
    private boolean isSelected;

    /**
     * Creates a {@code PrefixPredicate}.
     *
     * @param prefix the CLI prefix that identifies the target field.
     * @param predicateWrapper a factory that, given keywords, builds a field-level predicate.
     * @param isSelected initial selection state, usually false
     */
    public PrefixPredicateContainer(Prefix prefix, PredicateWrapper predicateWrapper, boolean isSelected) {
        this.prefix = prefix;
        this.predicateWrapper = predicateWrapper;
        this.isSelected = isSelected;
    }

    public static List<PrefixPredicateContainer> getAllPrefixPredicate() {
        return Arrays.asList(
                new PrefixPredicateContainer(
                        PREFIX_STUDENTID,
                        keywords -> new StudentIdContainsKeywordsPredicate(keywords),
                        false
                ),
                new PrefixPredicateContainer(
                        PREFIX_NAME,
                        keywords -> new NameContainsKeywordsPredicate(keywords),
                        false
                ),
                new PrefixPredicateContainer(
                        PREFIX_EMAIL,
                        keywords -> new EmailContainsKeywordsPredicate(keywords),
                        false
                ),
                new PrefixPredicateContainer(
                        PREFIX_GITHUB_USERNAME,
                        keywords -> new GithubContainsKeywordsPredicate(keywords),
                        false
                ),
                new PrefixPredicateContainer(
                        PREFIX_PHONE,
                        keywords -> new PhoneContainsKeywordsPredicate(keywords),
                false
                ),
                new PrefixPredicateContainer(
                        PREFIX_TAG,
                        keywords -> new TagContainsKeywordsPredicate(keywords),
                        false
                )
        );
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public PredicateWrapper getPredicateWrapper() {
        return predicateWrapper;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean set) {
        isSelected = set;
    }

    /**
     * Functional interface for building field-level predicates from a list of keywords.
     * Enables lazy building of only predicates that are selected.
     */
    public interface PredicateWrapper {

        public Predicate<Person> buildPredicate(List<String> keywords);
    }
}
