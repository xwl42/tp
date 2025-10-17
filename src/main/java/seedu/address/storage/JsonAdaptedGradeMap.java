package seedu.address.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.model.person.Examination;
import seedu.address.model.person.GradeMap;

/**
 * Jackson-friendly version of {@link GradeMap}.
 */
public class JsonAdaptedGradeMap {
    private Map<String, JsonAdaptedExamination> gradeMap = new HashMap<>();

    @JsonCreator
    public JsonAdaptedGradeMap() {

    }

    /**
     * Constructs a {@code JsonAdaptedGradeMap} from the given source {@code GradeMap}.
     */
    public JsonAdaptedGradeMap(GradeMap source) {
        this.gradeMap = source.getGradeableHashMap().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Examination)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new JsonAdaptedExamination((Examination) entry.getValue())
                ));
    }

    /**
     * Converts this JSON-adapted grade map into the model's {@code GradeMap} object.
     */
    public GradeMap toModelType() {
        GradeMap modelMap = new GradeMap();
        gradeMap.forEach((key, adaptedExam) -> {
            Examination exam = adaptedExam.toModelType();
            modelMap.putExam(key, exam);
        });
        return modelMap;
    }

    public Map<String, JsonAdaptedExamination> getGradeMap() {
        return gradeMap;
    }

    public void setGradeMap(Map<String, JsonAdaptedExamination> gradeMap) {
        this.gradeMap = gradeMap;
    }
}
