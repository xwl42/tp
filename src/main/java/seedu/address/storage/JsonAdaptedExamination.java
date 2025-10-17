package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.Examination;
import seedu.address.model.person.exceptions.InvalidScoreException;


/**
 * Jackson-friendly version of {@link Examination}.
 */
public class JsonAdaptedExamination {
    private String name;
    private Double score;
    /**
     * Constructs a {@code JsonAdaptedExamination} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedExamination(
            @JsonProperty("name") String name,
            @JsonProperty("score") String score
    ) {
        this.name = name;
        this.score = Double.parseDouble(score);
    }

    /**
     * Constructs a JSON adapted examination from the source
     * @param source from which exam is created
     */
    public JsonAdaptedExamination(Examination source) {
        this.name = source.getName();
        this.score = source.getScore();
    }

    /**
     * @return an examination from the JsonAdaptedExamination
     */
    public Examination toModelType() {
        Examination exam = new Examination(name);
        if (score != null && score >= 0) {
            try {
                exam.setScore(score);
            } catch (InvalidScoreException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return exam;
    }
}
