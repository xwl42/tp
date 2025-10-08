package seedu.address.model.person;
import static seedu.address.model.person.Name.VALIDATION_REGEX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.logic.commands.Status;


/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class ExerciseTracker {

    public static final String MESSAGE_CONSTRAINTS = "Exercise tracker takes in statuses";

    public final ArrayList<Status> statuses;

    /**
     * Default constructor, initialises statuses to all be not done
     */
    public ExerciseTracker() {
        this.statuses = new ArrayList<>(
                Collections.nCopies(10, Status.NOT_DONE)
        );
    }
    /**
     * Alternative constructor, initialises statuses to an input arraylist
     */
    public ExerciseTracker(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return IntStream.range(0, statuses.size())
                .mapToObj(x -> String.format("exercise %d: %s \n", x, statuses.get(x)))
                .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return statuses.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return statuses.hashCode();
    }
}

