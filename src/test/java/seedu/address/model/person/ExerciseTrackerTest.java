package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LAB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LAB;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

public class ExerciseTrackerTest {

    private ExerciseTracker tracker;

    @BeforeEach
    public void setUp() {
        tracker = new ExerciseTracker();
    }
    @Test
    public void isValidExerciseTracker_validFormat_returnsTrue() {
        String valid = "ex 0: N ex 1: D ex 2: O ex 3: I ex 4: N ex 5: N ex 6: D ex 7: O ex 8: N ex 9: I";
        assertTrue(ExerciseTracker.isValidExerciseTracker(valid));
    }

    @Test
    public void constructor_default_initializesAllToNotDone() {
        ArrayList<Status> statuses = tracker.getStatuses();
        assertEquals(10, statuses.size());
        assertTrue(statuses.stream().allMatch(s -> s == Status.NOT_DONE));
    }
    @Test
    public void markExercise_invalidIndex_throwsIndexOutOfBounds() {
        Index index1 = Index.fromZeroBased(100);
        assertThrows(IndexOutOfBoundsException.class, ()
                -> tracker.markExercise(index1, Status.DONE));
    }

    @Test
    public void markExercise_validIndex_updatesStatus() {
        Index index = Index.fromZeroBased(3);
        tracker.markExercise(index, Status.DONE);
        assertEquals(Status.DONE, tracker.getStatuses().get(3));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(tracker.equals(tracker));
    }

    @Test
    public void equals_sameStatuses_returnsTrue() {
        ExerciseTracker other = new ExerciseTracker(new ArrayList<>(tracker.getStatuses()));
        assertTrue(tracker.equals(other));
        assertEquals(tracker.hashCode(), other.hashCode());
    }

    @Test
    public void equals_differentStatuses_returnsFalse() {
        ExerciseTracker other = new ExerciseTracker();
        other.markExercise(Index.fromZeroBased(1), Status.DONE);
        assertFalse(tracker.equals(other));
    }

    @Test
    public void toString_containsExpectedFormat() {
        String result = tracker.toString();
        assertTrue(result.contains("ex 0: N"));
        assertTrue(result.contains("ex 9: N"));
    }

    @Test
    public void calculateProgress() {
        ExerciseTracker exerciseTracker = new ExerciseTracker();
        assertEquals(0.0 / ExerciseTracker.NUMBER_OF_EXERCISES * 100, exerciseTracker.calculateProgress());

        // TODO: Change name of the index later
        exerciseTracker.markExercise(INDEX_FIRST_LAB, Status.DONE);
        assertEquals(1.0 / ExerciseTracker.NUMBER_OF_EXERCISES * 100, exerciseTracker.calculateProgress());

        exerciseTracker.markExercise(INDEX_SECOND_LAB, Status.DONE);
        assertEquals(2.0 / ExerciseTracker.NUMBER_OF_EXERCISES * 100, exerciseTracker.calculateProgress());

        exerciseTracker.markExercise(INDEX_SECOND_LAB, Status.OVERDUE);
        assertEquals(0.5 / ExerciseTracker.NUMBER_OF_EXERCISES * 100, exerciseTracker.calculateProgress());

        exerciseTracker.markExercise(INDEX_FIRST_LAB, Status.OVERDUE);
        assertEquals(-1.0 / ExerciseTracker.NUMBER_OF_EXERCISES * 100, exerciseTracker.calculateProgress());
    }

}
