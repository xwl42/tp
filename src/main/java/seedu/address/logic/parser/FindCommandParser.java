package seedu.address.logic.parser;

import static java.lang.Boolean.FALSE;
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
import seedu.address.model.person.PrefixPredicate;
import seedu.address.model.person.keywordpredicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.keywordpredicate.GithubContainsKeywordsPredicate;
import seedu.address.model.person.keywordpredicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.keywordpredicate.PersonContainsKeywordsPredicate;
import seedu.address.model.person.keywordpredicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.keywordpredicate.StudentIdContainsKeywordsPredicate;
import seedu.address.model.person.keywordpredicate.TagContainsKeywordsPredicate;

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

        List<PrefixPredicate> prefixPredicates = PrefixPredicate.getAllPrefixPredicate();
        boolean isSeleceted = false;
        List<Predicate<Person>> predicates = new ArrayList<>();

        for (int i = 0; i < prefixPredicates.size(); i++) {
            PrefixPredicate prefixPredicate = prefixPredicates.get(i);
            if (argMultimap.getValue(
                    prefixPredicate.getPrefix()).isPresent()) {
                prefixPredicate.setIsSelected(TRUE);
                isSeleceted = true;
            }
        }

        for (int i = 0; i < prefixPredicates.size(); i++) {
            PrefixPredicate prefixPredicate = prefixPredicates.get(i);
            if (!isSeleceted || prefixPredicate.getIsSelected()) {
                predicates.add(prefixPredicate
                        .getPredicateWrapper()
                        .buildPredicate(keywords));
            }
        }

        return predicates;
    }

}
