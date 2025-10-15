package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 */
public class Student extends Person {

    // each student can have one parent
    private PersonId linkedParentId;
    private PersonId linkedTutorId;

    /**
     * Constructs a {@code Student}.
     */
    public Student(Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(category, name, phone, email, address, tags);
        this.linkedParentId = null;
        this.linkedTutorId = null;
    }

    /**
     * Constructs a {@code Student} with a given ID.
     */
    public Student(PersonId id, Category category, Name name, Phone phone, Email email,
                    Address address, Set<Tag> tags) {
        super(id, category, name, phone, email, address, tags);
        this.linkedParentId = null;
        this.linkedTutorId = null;
    }

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

    public PersonId getTutorId() {
        return linkedTutorId;
    }

    public void setTutor(Tutor tutor) {
        requireNonNull(tutor);
        setTutorId(tutor.getId());
    }

    public void setTutorId(PersonId tutorId) {
        requireNonNull(tutorId);
        if (tutorId.equals(this.linkedTutorId)) {
            return;
        }
        this.linkedTutorId = tutorId;
    }

    public void clearParent() {
        this.linkedParentId = null;
    }

    public void clearTutor() {
        this.linkedTutorId = null;
    }
}
