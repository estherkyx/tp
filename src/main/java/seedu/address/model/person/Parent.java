package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Parent in the address book.
 */
public class Parent extends Person {

    // each parent can have multiple children (students)
    private final Set<PersonId> childrenIds = new HashSet<>();

    public Parent(String category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(category, name, phone, email, address, tags);
    }

    public Parent(PersonId id, String category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(id, category, name, phone, email, address, tags);
    }

    public Set<PersonId> getChildrenIds() {
        return this.childrenIds;
    }

    /**
     * Returns true if the parent has the given student.
     */
    public boolean hasChild(Student student) {
        requireNonNull(student);
        return childrenIds.contains(student.getId());
    }

    /**
     * Returns true if the parent has the given student id.
     */
    public boolean hasChildId(PersonId childId) {
        requireNonNull(childId);
        return childrenIds.contains(childId);
    }

    /**
     * Adds the given student's ID to the parent's children IDs via a student object.
     */
    public void addChild(Student student) {
        requireNonNull(student);
        addChildId(student.getId());
    }

    /**
     * Adds the given student's ID to the parent's children IDs via a student id.
     */
    public void addChildId(PersonId childId) {
        requireNonNull(childId);
        if (childrenIds.contains(childId)) {
            return; // child already added, so ignore
        }
        childrenIds.add(childId);
    }

    /**
     * Removes the given student's ID from the parent's children IDs via a student object.
     */
    public void removeChild(Student student) {
        requireNonNull(student);
        removeChildId(student.getId());
    }

    /**
     * Removes the given student's ID from the parent's children IDs via a student id.
     */
    public void removeChildId(PersonId childId) {
        requireNonNull(childId);
        childrenIds.remove(childId);
    }
}
