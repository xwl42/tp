package seedu.address.logic.parser;

import static java.lang.Boolean.TRUE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.GithubContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PersonContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicates.StudentIdContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENTID, PREFIX_NAME,
                        PREFIX_EMAIL, PREFIX_GITHUB_USERNAME, PREFIX_PHONE, PREFIX_TAG);
        String[] preamble = argMultimap.getPreamble().trim().split("\\s+");
        List<String> keywords = Arrays.asList(preamble);

        if (keywords.isEmpty() || keywords.get(0).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Predicate<Person>> predicates = selectPredicates(argMultimap, keywords);

        return new FindCommand(new PersonContainsKeywordsPredicate(predicates));
    }

    private List<Predicate<Person>> selectPredicates(ArgumentMultimap argMultimap, List<String> keywords) {

        List<PrefixPredicateContainer> prefixPredicateContainers = PrefixPredicateContainer.getAllPrefixPredicate();
        boolean isAnySelected = false;
        List<Predicate<Person>> predicates = new ArrayList<>();

        for (int i = 0; i < prefixPredicateContainers.size(); i++) {
            PrefixPredicateContainer prefixPredicateContainer = prefixPredicateContainers.get(i);
            if (argMultimap.getValue(
                    prefixPredicateContainer.getPrefix()).isPresent()) {
                prefixPredicateContainer.setIsSelected(TRUE);
                isAnySelected = true;
            }
        }

        for (int i = 0; i < prefixPredicateContainers.size(); i++) {
            PrefixPredicateContainer prefixPredicateContainer = prefixPredicateContainers.get(i);
            if (!isAnySelected || prefixPredicateContainer.getIsSelected()) {
                predicates.add(prefixPredicateContainer
                        .getPredicateWrapper()
                        .buildPredicate(keywords));
            }
        }

        return predicates;
    }

    /**
     * Binds a {@link Prefix} (e.g., {@code n/}, {@code e/}) to a predicate “builder” for a {@link Person}
     * and tracks whether that field has been selected as a search target.
     */
    public static class PrefixPredicateContainer {
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
}
