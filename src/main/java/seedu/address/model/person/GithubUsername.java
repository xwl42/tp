package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's GitHub Username in the githubUsername book.
 * Guarantees: immutable
 */
public class GithubUsername {

    public static final String MESSAGE_CONSTRAINTS = "GitHub usernames can take any values, and it should not be blank";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    // Correct Regex: ^(?!.*--)(?!-)[a-zA-Z0-9-]{1,39}(?<!-)$
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code GithubUsername}.
     *
     * @param username A valid GitHub username.
     */
    public GithubUsername(String username) {
        requireNonNull(username);
        checkArgument(isValidGithubUsername(username), MESSAGE_CONSTRAINTS);
        value = username;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidGithubUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GithubUsername)) {
            return false;
        }

        GithubUsername otherGithubUsername = (GithubUsername) other;
        return value.equals(otherGithubUsername.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
