package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

/**
 * Represents a Student in the address book.
 */
public class Student extends Person {

    // each student can have one parent
    private PersonId linkedParentId;
    private Day tuitionDay;
    private Time tuitionTime;

    /**
     * Constructs a {@code Student}.
     */
    public Student(Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(category, name, phone, email, address, tags);
        this.linkedParentId = null;
        this.tuitionDay = null;
        this.tuitionTime = null;
    }

    /**
     * Constructs a {@code Student} with a given ID.
     */
    public Student(PersonId id, Category category, Name name, Phone phone, Email email,
                    Address address, Set<Tag> tags) {
        super(id, category, name, phone, email, address, tags);
        this.linkedParentId = null;
    }

    @Override
    public PersonId getParentId() {
        return linkedParentId;
    }

    public void setParent(Parent parent) {
        requireNonNull(parent);
        setParentId(parent.getId());
    }

    public void setParentId(PersonId parentId) {
        requireNonNull(parentId);
        if (parentId.equals(this.linkedParentId)) {
            return; // no change
        }
        this.linkedParentId = parentId;
    }

    public void clearParent() {
        this.linkedParentId = null;
    }

    public Optional<Day> getTuitionDay() {
        return Optional.ofNullable(tuitionDay);
    }

    public Optional<Time> getTuitionTime() {
        return Optional.ofNullable(tuitionTime);
    }

    public void setTuitionClass(Day day, Time time) {
        this.tuitionDay = day;
        this.tuitionTime = time;
    }

}
