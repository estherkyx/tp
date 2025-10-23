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
    private final Set<PersonId> studentIds = new HashSet<>();


    /**
     * Creates a new {@code TuitionClass} scheduled on the given day and time,
     * with no tutor assigned and no students enrolled.
     *
     * @param day  the day of the week for this class; must not be {@code null}
     * @param time the time slot for this class; must not be {@code null}
     * @throws NullPointerException if {@code day} or {@code time} is {@code null}
     */
    public TuitionClass(Day day, Time time) {
        requireAllNonNull(day, time);
        this.day = day;
        this.time = time;
        this.tutorId = null; // No tutor assigned yet
    }

    /**
     * Creates a new {@code TuitionClass} scheduled on the given day and time,
     * with the specified tutor and initial set of students.
     *
     * @param day        the day of the week for this class; must not be {@code null}
     * @param time       the time slot for this class; must not be {@code null}
     * @param tutorId    the identifier of the tutor; must not be {@code null}
     * @param studentIds the initial set of student identifiers; must not be {@code null}
     * @throws NullPointerException if any parameter is {@code null}
     */
    public TuitionClass(Day day, Time time, PersonId tutorId, Set<PersonId> studentIds) {
        requireAllNonNull(day, time, tutorId, studentIds);
        this.day = day;
        this.time = time;
        this.tutorId = tutorId;
        this.studentIds.addAll(studentIds);
    }

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }

    public String getTimeString() {
        return this.getTime().toString().substring(1) + "00";
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

    public void removeStudentId(PersonId studentId) {
        this.studentIds.remove(studentId);
    }

    /**
     * Sets the tutor for this class.
     * @param tutorId The PersonId of the new tutor.
     */
    public void setTutorId(PersonId tutorId) {
        requireNonNull(tutorId);
        this.tutorId = tutorId;
    }

    public void removeTutorId() {
        this.tutorId = null;
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
