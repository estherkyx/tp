package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

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
    private final String tutorId;
    private final List<String> studentIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTuitionClass} with the given details.
     */
    @JsonCreator
    public JsonAdaptedTuitionClass(@JsonProperty("day") String day, @JsonProperty("time") String time,
                                   @JsonProperty("tutorId") String tutorId,
                                   @JsonProperty("studentIds") List<String> studentIds) {
        this.day = day;
        this.time = time;
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
        tutorId = (source.getTutorId() == null) ? null : source.getTutorId().getValue();
        studentIds.addAll(source.getStudentIds().stream()
                .map(PersonId::getValue)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted class object into the model's {@code TuitionClass} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted class.
     */
    public TuitionClass toModelType() throws IllegalValueException {
        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        final Day modelDay;
        try {
            modelDay = Day.fromString(day);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        final Time modelTime;
        try {
            modelTime = Time.fromString(time);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }

        final Set<PersonId> modelStudentIds = new HashSet<>();
        for (String studentId : studentIds) {
            modelStudentIds.add(PersonId.of(studentId));
        }

        // Create the base class first
        TuitionClass tuitionClass = new TuitionClass(modelDay, modelTime);

        // If a tutorId exists in the JSON, add it to the class object
        if (tutorId != null) {
            tuitionClass.setTutorId(PersonId.of(tutorId));
        }

        // Add all students to the class object
        for (PersonId studentId : modelStudentIds) {
            tuitionClass.addStudentId(studentId);
        }

        return tuitionClass;
    }
}
