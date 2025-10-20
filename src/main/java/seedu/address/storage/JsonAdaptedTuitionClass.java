package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PersonId;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Jackson-friendly version of {@link TuitionClass}.
 */
class JsonAdaptedTuitionClass {

    private final String day;
    private final String time;
    private final int rate;
    private final String tutorId;
    private final List<String> studentIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTuitionClass} with the given details.
     */
    @JsonCreator
    public JsonAdaptedTuitionClass(@JsonProperty("day") String day, @JsonProperty("time") String time,
                                   @JsonProperty("rate") int rate, @JsonProperty("tutorId") String tutorId,
                                   @JsonProperty("studentIds") List<String> studentIds) {
        this.day = day;
        this.time = time;
        this.rate = rate;
        this.tutorId = tutorId;
        if (studentIds != null) {
            this.studentIds.addAll(studentIds);
        }
    }

    /**
     * Converts a given {@code TuitionClass} into this class for Jackson use.
     */
    public JsonAdaptedTuitionClass(TuitionClass source) {
        day = source.getDay().toString();
        time = source.getTime().toString();
        rate = source.getRate();
        tutorId = source.getTutorId().getValue();
        studentIds.addAll(source.getStudentIds().stream()
                .map(PersonId::getValue)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted class object into the model's {@code TuitionClass} object.
     */
    public TuitionClass toModelType() throws IllegalValueException {
        try {
            final Day modelDay = Day.fromString(day);
            final Time modelTime = Time.fromString(time);
            final int modelRate = rate;
            final PersonId modelTutorId = PersonId.of(tutorId);
            final Set<PersonId> modelStudentIds = new HashSet<>();
            for (String studentId : studentIds) {
                modelStudentIds.add(PersonId.of(studentId));
            }

            return new TuitionClass(modelDay, modelTime, modelRate, modelTutorId, modelStudentIds);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid data found in storage: " + e.getMessage());
        }
    }
}
