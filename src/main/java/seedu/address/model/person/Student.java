package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tuitionclass.ClassId;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Represents a Student in the address book.
 */
public class Student extends Person {

    // each student can have one parent
    private PersonId linkedParentId;
    private ClassId classId;

    /**
     * Constructs a {@code Student}.
     */
    public Student(Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(category, name, phone, email, address, tags);
        this.linkedParentId = null;
        this.classId = null; // Initialize to null
    }

    /**
     * Constructs a {@code Student} with a given ID.
     */
    public Student(PersonId id, Category category, Name name, Phone phone, Email email,
                    Address address, Set<Tag> tags) {
        super(id, category, name, phone, email, address, tags);
        this.linkedParentId = null;
        this.classId = null;
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
            return;
        }
        this.linkedParentId = parentId;
    }

    public void clearParent() {
        this.linkedParentId = null;
    }

    /**
     * Returns the Student's ClassId, if they are enrolled.
     */
    public Optional<ClassId> getClassId() {
        return Optional.ofNullable(classId);
    }

    /**
     * Enrolls the student in a tuition class by storing its unique ClassId.
     *
     * @param classId The unique identifier of the class to enroll in.
     */
    public void setTuitionClass(ClassId classId) {
        this.classId = classId;
    }

    /**
     * Overloaded helper method for convenience.
     * Extracts the ClassId from a TuitionClass object and sets it.
     */
    public void setTuitionClass(TuitionClass tuitionClass) {
        requireNonNull(tuitionClass);
        this.classId = tuitionClass.getClassId();
    }

    /**
     * Clears the tuition class info from a student.
     */
    public void clearTuitionClass() {
        this.classId = null;
    }

    /**
     * Finds and returns the student's TuitionClass from a given list of all classes.
     * This is a helper method to bridge the student's ClassId with the actual class object.
     *
     * @param allTuitionClasses The list of all classes in the system.
     * @return An Optional containing the TuitionClass if found.
     */
    public Optional<TuitionClass> findTuitionClass(List<TuitionClass> allTuitionClasses) {
        if (getClassId().isEmpty()) {
            return Optional.empty();
        }
        ClassId targetClassId = getClassId().get();
        return allTuitionClasses.stream()
                .filter(tuitionClass -> tuitionClass.getClassId().equals(targetClassId))
                .findFirst();
    }
}
