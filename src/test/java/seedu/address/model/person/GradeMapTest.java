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
        assertEquals(30.0 / Examination.MAX_MIDTERM_SCORE * 100.0 , exam.getScore());
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
    @Test
    public void copy_validGrades_createsDeepCopy() throws InvalidExamNameException {
        gradeMap.gradeExam("midterm", 40.0);
        gradeMap.gradeExam("final", 90.0);

        GradeMap copied = gradeMap.copy();

        assertNotNull(copied, "Copy should not be null");
        assertNotSame(gradeMap, copied, "Copy should be a different object");
        assertEquals(gradeMap, copied, "Copy should have the same content initially");

        // Verify all exam entries exist
        for (String examName : GradeMap.VALID_EXAM_NAMES) {
            assertTrue(copied.getGradeableHashMap().containsKey(examName),
                    "Copied GradeMap should contain " + examName);
            assertNotNull(copied.getGradeableHashMap().get(examName),
                    "Copied exam entry should not be null");
        }

        // Verify scores were copied correctly
        assertEquals(
                gradeMap.getGradeableHashMap().get("midterm").getScore(),
                copied.getGradeableHashMap().get("midterm").getScore(),
                1e-9,
                "Midterm score should match after copy"
        );
        assertEquals(
                gradeMap.getGradeableHashMap().get("final").getScore(),
                copied.getGradeableHashMap().get("final").getScore(),
                1e-9,
                "Final score should match after copy"
        );

        // Deep copy check — modifying copy shouldn’t affect original
        copied.gradeExam("midterm", 60.0);
        double originalMidterm = gradeMap.getGradeableHashMap().get("midterm").getScore();
        double copiedMidterm = copied.getGradeableHashMap().get("midterm").getScore();

        assertNotEquals(
                originalMidterm,
                copiedMidterm,
                "Changing copy's midterm score should not affect the original GradeMap"
        );
    }

    @Test
    public void copy_ungradedExams_preserveDefaultState() {
        GradeMap copied = gradeMap.copy();

        for (String examName : GradeMap.VALID_EXAM_NAMES) {
            assertTrue(copied.getGradeableHashMap().containsKey(examName),
                    "Copied GradeMap should contain " + examName);
            Gradeable exam = copied.getGradeableHashMap().get(examName);
            assertTrue(exam instanceof Examination, "Exam should be an instance of Examination");
            double score = ((Examination) exam).getScore();
            assertEquals(-1.0, score, "Ungraded exam should have score -1.0");
        }
    }
}
