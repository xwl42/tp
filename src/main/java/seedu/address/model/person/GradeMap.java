package seedu.address.model.person;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Wraps a HashMap with String keys and Gradeable values.
 */
public class GradeMap {
    private final HashMap<String, Gradeable> gradeableHashMap;
    private final String[] assessments = {"pe1", "midterms", "pe2", "finals"};
    private final double[] maxScores = {40.0, 60.0, 40.0, 100.0};
    /**
     * Fills the hashmap with the keys
     */
    public GradeMap() {
        gradeableHashMap = new HashMap<>();
        for (int i = 0; i < assessments.length; i++) {
            gradeableHashMap.put(
                    assessments[i],
                    new Examination(assessments[i], maxScores[i])
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
}

