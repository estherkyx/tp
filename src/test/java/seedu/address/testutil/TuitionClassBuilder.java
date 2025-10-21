package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * A utility class to help with building TuitionClass objects.
 */
public class TuitionClassBuilder {

    public static final Day DEFAULT_DAY = Day.MONDAY;
    public static final Time DEFAULT_TIME = Time.H12;

    public static final Person DEFAULT_TUTOR = GEORGE;

    private Day day;
    private Time time;
    private PersonId tutorId;
    private Set<PersonId> studentIds;

    /**
     * Creates a {@code TuitionClassBuilder} with the default details.
     */
    public TuitionClassBuilder() {
        day = DEFAULT_DAY;
        time = DEFAULT_TIME;
        tutorId = DEFAULT_TUTOR.getId();
        studentIds = new HashSet<>();
    }

    /**
     * Initializes the TuitionClassBuilder with the data of {@code classToCopy}.
     */
    public TuitionClassBuilder(TuitionClass classToCopy) {
        day = classToCopy.getDay();
        time = classToCopy.getTime();
        tutorId = classToCopy.getTutorId();
        studentIds = new HashSet<>(classToCopy.getStudentIds());
    }

    /**
     * Sets the day of the tuition class being built.
     *
     * @param day the day to set
     * @return this builder for chaining
     */
    public TuitionClassBuilder withDay(Day day) {
        this.day = day;
        return this;
    }

    /**
     * Sets the time of the tuition class being built.
     *
     * @param time the time to set
     * @return this builder for chaining
     */
    public TuitionClassBuilder withTime(Time time) {
        this.time = time;
        return this;
    }

    /**
     * Sets the tutor of the tuition class being built.
     *
     * @param tutor the tutor to assign
     * @return this builder for chaining
     */
    public TuitionClassBuilder withTutor(Person tutor) {
        this.tutorId = tutor.getId();
        return this;
    }

    /**
     * Add students (by Person) to this tuition class builder.
     */
    public TuitionClassBuilder withStudents(Person... students) {
        Arrays.stream(students).map(Person::getId).forEach(studentIds::add);
        return this;
    }

    /**
     * Builds and returns a {@link TuitionClass} object based on the
     * current state of this builder.
     *
     * @return the constructed TuitionClass
     */
    public TuitionClass build() {
        TuitionClass tc = new TuitionClass(day, time);
        if (tutorId != null) {
            tc.setTutorId(tutorId);
        }
        for (PersonId pid : studentIds) {
            tc.addStudentId(pid);
        }
        return tc;
    }
}
