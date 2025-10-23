package seedu.address.logic;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Utility class providing helper methods to query tutors, classes, and students
 * from the model without duplicating logic across commands.
 *
 * <p>All methods are static and side-effect free — they do not modify the {@code Model}
 * or its data. Instead, they perform read-only lookups on persons and tuition classes
 * for use in higher-level commands such as {@code GetClassesCommand},
 * {@code GetClassDetailsCommand}, and {@code GetStudentsCommand}.</p>
 *
 * <p>This class is intentionally non-instantiable.</p>
 */
public final class ClassQueries {
    private ClassQueries() {}

    /**
     * Finds the first tutor in the model with the given name.
     *
     * @param model The model containing all persons.
     * @param name The name of the tutor to search for.
     * @return An {@code Optional<Tutor>} containing the tutor if found, or an empty {@code Optional} otherwise.
     */
    public static Optional<Tutor> findTutorByName(Model model, Name name) {
        return model.getAddressBook().getPersonList().stream()
                .filter(p -> p instanceof Tutor)
                .map(p -> (Tutor) p)
                .filter(t -> t.getName().equals(name))
                .findFirst();
    }

    /**
     * Retrieves all tuition classes taught by a specific tutor.
     *
     * @param model The model containing all tuition classes.
     * @param tutor The tutor whose classes are to be retrieved.
     * @return A list of {@code TuitionClass} objects associated with the given tutor.
     */
    public static List<TuitionClass> getClassesByTutor(Model model, Tutor tutor) {
        return model.getTuitionClassList().stream()
                .filter(tuitionClass -> tutor.getId().equals(tuitionClass.getTutorId()))
                .toList();
    }

    /**
     * Retrieves all students enrolled in a given tuition class, sorted alphabetically by name.
     *
     * @param model The model containing all persons.
     * @param tuitionClass The class whose students are to be retrieved.
     * @return A list of {@code Student} objects enrolled in the specified class.
     */
    public static List<Student> getStudentsInClass(Model model, TuitionClass tuitionClass) {
        return model.getAddressBook().getPersonList().stream()
                .filter(t -> tuitionClass.getStudentIds().contains(t.getId()))
                .filter(p -> p instanceof Student)
                .map(t -> (Student) t)
                .sorted(Comparator.comparing(a -> a.getName().toString()))
                .toList();
    }
}
