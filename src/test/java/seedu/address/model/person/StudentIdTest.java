package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidStudentId_throwsIllegalArgumentException() {
        String invalidStudentId = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentId(invalidStudentId));
    }

    @Test
    public void isValidStudentId() {
        // null studentId
        assertThrows(NullPointerException.class, () -> StudentId.isValidStudentId(null));

        // invalid student IDs
        assertFalse(StudentId.isValidStudentId("")); // empty string
        assertFalse(StudentId.isValidStudentId(" ")); // spaces only
        assertFalse(StudentId.isValidStudentId("A123456")); // only 6 digits
        assertFalse(StudentId.isValidStudentId("A12345678")); // 8 digits (too many)
        assertFalse(StudentId.isValidStudentId("A1234567X ")); // trailing space
        assertFalse(StudentId.isValidStudentId(" A1234567X")); // leading space
        assertFalse(StudentId.isValidStudentId("A1234567")); // missing letter at end
        assertFalse(StudentId.isValidStudentId("B1234567X")); // starts with B instead of A
        assertFalse(StudentId.isValidStudentId("1234567X")); // missing 'A' at start
        assertFalse(StudentId.isValidStudentId("A123456XX")); // extra letter at end

        // valid student IDs (case-insensitive, will be normalized to uppercase)
        assertTrue(StudentId.isValidStudentId("A0000000A"));
        assertTrue(StudentId.isValidStudentId("A1234567X"));
        assertTrue(StudentId.isValidStudentId("A7654321Z"));
        assertTrue(StudentId.isValidStudentId("A9999999Y"));
        assertTrue(StudentId.isValidStudentId("a1234567x")); // lowercase is valid (normalized to uppercase)
        assertTrue(StudentId.isValidStudentId("a9999999z")); // lowercase is valid (normalized to uppercase)
    }

    @Test
    public void equals() {
        StudentId studentId = new StudentId("A1234567X");

        // same values -> returns true
        assertTrue(studentId.equals(new StudentId("A1234567X")));

        // same values but different case -> returns true (normalized to uppercase)
        assertTrue(studentId.equals(new StudentId("a1234567x")));

        // same object -> returns true
        assertTrue(studentId.equals(studentId));

        // null -> returns false
        assertFalse(studentId.equals(null));

        // different types -> returns false
        assertFalse(studentId.equals(5.0f));

        // different values -> returns false
        assertFalse(studentId.equals(new StudentId("A7654321Z")));
    }
}
