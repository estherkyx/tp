package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tuitionclass.ClassId;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

/**
 * Jackson-friendly version of {@link ClassId}.
 */
class JsonAdaptedClassId {
    private final String day;
    private final String time;

    @JsonCreator
    public JsonAdaptedClassId(@JsonProperty("day") String day, @JsonProperty("time") String time) {
        this.day = day;
        this.time = time;
    }

    public JsonAdaptedClassId(ClassId source) {
        this.day = source.getDay().name();
        this.time = source.getTime().name();
    }

    public ClassId toModelType() throws IllegalValueException {
        try {
            return new ClassId(Day.fromString(day), Time.fromString(time));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalValueException("Invalid ClassId in JSON.");
        }
    }
}
