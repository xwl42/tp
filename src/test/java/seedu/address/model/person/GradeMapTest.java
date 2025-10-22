package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.InvalidExamNameException;

public class GradeMapTest {

    private GradeMap gradeMap;

    @BeforeEach
    public void setUp() {
        gradeMap = new GradeMap();
    }

    @Test
    public void constructor_initializesAllValidExamNames() {
        HashMap<String, Gradeable> map = gradeMap.getGradeableHashMap();
        assertEquals(GradeMap.VALID_EXAM_NAMES.length, map.size());
        for (String name : GradeMap.VALID_EXAM_NAMES) {
            assertTrue(map.containsKey(name));
            assertNotNull(map.get(name));
        }
    }

    @Test
    public void gradeExam_validExamName_setsScoreSuccessfully() throws InvalidExamNameException {
        gradeMap.gradeExam("midterm", 30.0);
        Gradeable exam = gradeMap.getGradeableHashMap().get("midterm");
        assertEquals(30.0, exam.getScore());
    }

    @Test
    public void gradeExam_invalidExamName_throwsException() {
        InvalidExamNameException exception = assertThrows(InvalidExamNameException.class, ()
                -> gradeMap.gradeExam("quiz", 50));
        assertTrue(exception.getMessage().contains("quiz"));
        assertTrue(exception.getMessage().contains("pe1"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(gradeMap.equals(gradeMap));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(gradeMap.equals("not a grade map"));
    }

    @Test
    public void equals_sameContent_returnsTrue() {
        GradeMap other = new GradeMap();
        assertEquals(gradeMap, other);
    }

    @Test
    public void equals_differentContent_returnsFalse() throws InvalidExamNameException {
        GradeMap other = new GradeMap();
        other.gradeExam("pe1", 20);
        assertNotEquals(gradeMap, other);
    }

    @Test
    public void toString_containsAllExamNames() {
        String output = gradeMap.toString();
        for (String exam : GradeMap.VALID_EXAM_NAMES) {
            assertTrue(output.contains(exam));
        }
    }
}
