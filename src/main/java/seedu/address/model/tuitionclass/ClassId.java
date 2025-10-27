package seedu.address.model.tuitionclass;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents the unique identifier of a Tuition Class.
 * A ClassId is defined by its Day and Time. This is an immutable value object.
 */
public final class ClassId {

    private final Day day;
    private final Time time;

    /**
     * Constructs a {@code ClassId}.
     *
     * @param day The day of the class.
     * @param time The time of the class.
     */
    public ClassId(Day day, Time time) {
        requireAllNonNull(day, time);
        this.day = day;
        this.time = time;
    }

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClassId)) {
            return false;
        }
        ClassId otherId = (ClassId) other;
        return day.equals(otherId.day) && time.equals(otherId.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time);
    }

    @Override
    public String toString() {
        return "ClassId [" + day + ", " + time + "]";
    }
}
