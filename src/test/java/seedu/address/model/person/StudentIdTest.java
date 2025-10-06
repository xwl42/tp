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
        assertFalse(StudentId.isValidStudentId("e123456")); // only 7 digits
        assertFalse(StudentId.isValidStudentId("E12345678")); // capital E
        assertFalse(StudentId.isValidStudentId("e12345678 ")); // trailing space
        assertFalse(StudentId.isValidStudentId(" e1234567")); // leading space
        assertFalse(StudentId.isValidStudentId("e1234567a")); // extra character
        assertFalse(StudentId.isValidStudentId("12345678")); // missing 'e'

        // valid student IDs
        assertTrue(StudentId.isValidStudentId("e0000000"));
        assertTrue(StudentId.isValidStudentId("e1234567"));
        assertTrue(StudentId.isValidStudentId("e7654321"));
        assertTrue(StudentId.isValidStudentId("e9999999"));
    }

    @Test
    public void equals() {
        StudentId studentId = new StudentId("e1234567");

        // same values -> returns true
        assertTrue(studentId.equals(new StudentId("e1234567")));

        // same object -> returns true
        assertTrue(studentId.equals(studentId));

        // null -> returns false
        assertFalse(studentId.equals(null));

        // different types -> returns false
        assertFalse(studentId.equals(5.0f));

        // different values -> returns false
        assertFalse(studentId.equals(new StudentId("e7654321")));
    }
}
