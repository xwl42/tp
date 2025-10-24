package seedu.address.model.person;

import static seedu.address.logic.parser.GradeCommandParser.MESSAGE_INVALID_EXAM_NAME_FORMAT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.exceptions.InvalidExamNameException;
import seedu.address.model.person.exceptions.InvalidScoreException;

/**
 * Wraps a HashMap with String keys and Gradeable values.
 */
public class GradeMap {
    public static final String[] VALID_EXAM_NAMES = {"pe1", "midterm", "pe2", "final"};
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private final HashMap<String, Gradeable> gradeableHashMap;
    /**
     * Fills the hashmap with the keys
     */
    public GradeMap() {
        gradeableHashMap = new HashMap<>();
        for (String assessment : VALID_EXAM_NAMES) {
            gradeableHashMap.put(
                    assessment,
                    new Examination(assessment)
            );
        }
    }
    @Override
    public String toString() {
        return Arrays.stream(VALID_EXAM_NAMES)
                .map(a -> gradeableHashMap.get(a).toString())
                .collect(Collectors.joining(", "));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // same reference
        }
        if (!(obj instanceof GradeMap)) {
            return false; // different type
        }
        GradeMap other = (GradeMap) obj;
        return this.gradeableHashMap.equals(other.gradeableHashMap);
    }

    public HashMap<String, Gradeable> getGradeableHashMap() {
        return gradeableHashMap;
    }

    public void putExam(String key, Examination exam) {
        gradeableHashMap.put(key, exam);
    }

    /**
     * Grades an exam with a score
     * @param name of the exam to be graded
     * @param score to grade the exam with
     * @throws InvalidExamNameException if the exam name is not in the list of valid exam names
     */
    public void gradeExam(String name, double score) throws InvalidExamNameException {
        logger.info(String.format("Grading %s with %.2f", name, score));
        Gradeable exam = gradeableHashMap.get(name);
        if (exam == null) {
            throw new InvalidExamNameException(
                    String.format(MESSAGE_INVALID_EXAM_NAME_FORMAT,
                        name,
                        Arrays.toString(VALID_EXAM_NAMES)
                    )
            );
        }
        exam.setScore(score);
    }

    /**
     * Returns a deep copy of this GradeMap.
     * @return a new GradeMap with copied data
     */
    public GradeMap copy() {
        GradeMap newGradeMap = new GradeMap();

        for (String examName : VALID_EXAM_NAMES) {
            Gradeable original = this.gradeableHashMap.get(examName);
            Gradeable copied = newGradeMap.gradeableHashMap.get(examName);
            if (original instanceof Examination) {
                Examination originalExam = (Examination) original;
                if (originalExam.getScore() != -1.0) {
                    try {
                        copied.setPercentageScore(originalExam.getScore());
                    } catch (InvalidScoreException e) {
                        throw new RuntimeException("Unexpected error copying valid score", e);
                    }
                }
            }
        }
        return newGradeMap;
    }
}

