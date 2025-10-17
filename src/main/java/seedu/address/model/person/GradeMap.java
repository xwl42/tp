package seedu.address.model.person;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Wraps a HashMap with String keys and Gradeable values.
 */
public class GradeMap {
    private final HashMap<String, Gradeable> gradeableHashMap;
    private final String[] assessments = {"pe1", "midterms", "pe2", "finals"};
    /**
     * Fills the hashmap with the keys
     */
    public GradeMap() {
        gradeableHashMap = new HashMap<>();
        for (String assessment : assessments) {
            gradeableHashMap.put(
                    assessment,
                    new Examination(assessment)
            );
        }
    }
    @Override
    public String toString() {
        return gradeableHashMap
                .values()
                .stream()
                .map(Object::toString)
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
}

