package seedu.address.model.tuitionclass;

import static java.util.Objects.requireNonNull;
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
    private PersonId tutorId;
    private Set<PersonId> studentIds = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public TuitionClass(Day day, Time time, PersonId tutorId, Set<PersonId> studentIds) {
        requireAllNonNull(day, time, tutorId, studentIds);
        this.day = day;
        this.time = time;
        this.tutorId = tutorId;
        this.studentIds.addAll(studentIds);
    }

    /**
     * Constructor for a new class time slot.
     * Tutor is initialised to null.
     */
    public TuitionClass(Day day, Time time) {
        requireAllNonNull(day, time);
        this.day = day;
        this.time = time;
        this.tutorId = null; // No tutor assigned yet
    }

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
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
     * Sets the tutor for this class.
     * @param tutorId The PersonId of the new tutor.
     */
    public void setTutorId(PersonId tutorId) {
        requireNonNull(tutorId);
        this.tutorId = tutorId;
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
