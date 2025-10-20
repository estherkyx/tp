package seedu.address.model.tuitionclass;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.PersonId;

/**
 * Represents a Tuition Class in the address book.
 * A class is uniquely identified by its Day and Time.
 */
public class TuitionClass {

    private final Day day;
    private final Time time;
    private final int rate;
    private final PersonId tutorId;
    private final Set<PersonId> studentIds = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public TuitionClass(Day day, Time time, int rate, PersonId tutorId, Set<PersonId> studentIds) {
        requireAllNonNull(day, time, tutorId, studentIds);
        this.day = day;
        this.time = time;
        this.rate = rate;
        this.tutorId = tutorId;
        this.studentIds.addAll(studentIds);
    }

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }

    public int getRate() {
        return rate;
    }

    public PersonId getTutorId() {
        return tutorId;
    }

    public Set<PersonId> getStudentIds() {
        return Collections.unmodifiableSet(studentIds);
    }

    public void addStudentId(PersonId studentId) {
        this.studentIds.add(studentId);
    }

    /**
     * Returns true if both tuition classes are at the same time on the same day.
     * This defines the uniqueness of a class.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TuitionClass)) {
            return false;
        }

        TuitionClass otherClass = (TuitionClass) other;
        return day.equals(otherClass.day)
                && time.equals(otherClass.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time);
    }
}
