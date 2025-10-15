package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Tutor in the address book.
 */
public class Tutor extends Person {

    // each tutor can have multiple students
    private final Set<PersonId> linkedStudentIds = new HashSet<>();

    public Tutor(Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(category, name, phone, email, address, tags);
    }

    public Tutor(PersonId id, Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(id, category, name, phone, email, address, tags);
    }

    public Set<PersonId> getStudentsIds() {
        return Collections.unmodifiableSet(linkedStudentIds);
    }

    /**
     * Returns true if the tutor has the given student via a student object.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return linkedStudentIds.contains(student.getId());
    }

    /**
     * Returns true if the tutor has the given student via a student id.
     */
    public boolean hasStudentId(PersonId studentId) {
        requireNonNull(studentId);
        return linkedStudentIds.contains(studentId);
    }

    /**
     * Adds the given student to the tutor via a student object.
     */
    public void addStudent(Student student) {
        requireNonNull(student);
        addStudentId(student.getId());
    }

    /**
     * Adds the given student to the tutor via a student id.
     */
    public void addStudentId(PersonId studentId) {
        requireNonNull(studentId);
        if (linkedStudentIds.contains(studentId)) {
            return; // student already added, so ignore
        }
        linkedStudentIds.add(studentId);
    }

    /**
     * Removes the given student from the tutor via a student object.
     */
    public void removeStudent(Student student) {
        requireNonNull(student);
        removeStudentId(student.getId());
    }

    /**
     * Removes the given student from the tutor via a student id.
     */
    public void removeStudentId(PersonId studentId) {
        requireNonNull(studentId);
        linkedStudentIds.remove(studentId);
    }
}
