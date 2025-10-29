package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.ClassId;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<TuitionClass> PREDICATE_SHOW_ALL_CLASSES = unused -> true;

    //=========== UserPrefs ==================================================================================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    //=========== AddressBook ================================================================================

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given parent.
     * {@code parent} must not already exist in the address book.
     */
    void addParent(Parent parent);

    /**
     * Adds the given tutor.
     * {@code tutor} must not already exist in the address book.
     */
    void addTutor(Tutor tutor);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Finds and returns a person by their exact name.
     * @param name The name of the person to find.
     * @return An Optional containing the Person if found, or an empty Optional otherwise.
     */
    List<Person> findPersonByName(Name name);

    /**
     * Finds and returns a person by their unique ID.
     * @return An Optional containing the Person if found, or an empty Optional otherwise.
     */
    Optional<Person> findPersonById(PersonId id);

    //=========== TuitionClass =====================================================================

    /**
     * Returns true if a tuition class with the same identity as {@code tuitionClass} exists in the address book.
     */
    boolean hasTuitionClass(TuitionClass tuitionClass);

    /**
     * Adds the given tuition class.
     * {@code tuitionClass} must not already exist in the address book.
     */
    void addTuitionClass(TuitionClass tuitionClass);

    /**
     * Replaces the given tuition class {@code target} with {@code editedTuitionClass}.
     * {@code target} must exist in the address book.
     * The tuition class identity of {@code editedTuitionClass} must not be the same as another existing
     * tuition class in the address book.
     */
    void setTuitionClass(TuitionClass target, TuitionClass editedTuitionClass);

    /**
     * Finds and returns a tuition class by its exact timeslot.
     * @param classId The Class ID of the class.
     * @return An Optional containing the TuitionClass if found, or an empty Optional otherwise.
     */
    Optional<TuitionClass> findTuitionClass(ClassId classId);

    /**
     * Returns an unmodifiable view of the tuition class list.
     */
    ObservableList<TuitionClass> getTuitionClassList();

    /**
     * Returns all tuition classes taught by a specific tutor.
     * @param tutor The tutor whose classes are to be retrieved.
     * @return A list of {@code TuitionClass} objects associated with the given tutor.
     */
    List<TuitionClass> getClassesByTutor(Tutor tutor);

    /**
     * Retrieves all students enrolled in a given tuition class, sorted alphabetically by name.
     *
     * @param tuitionClass The class which students are to be retrieved.
     * @return A list of {@code Student} objects enrolled in the specified class.
     */
    List<Student> getStudentsInClass(TuitionClass tuitionClass);

    //=========== Filtered Tuition Class List Accessors ============================================

    /** Returns an unmodifiable view of the filtered tuition class list */
    ObservableList<TuitionClass> getFilteredTuitionClassList();

    /**
     * Updates the filter of the filtered tuition class list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTuitionClassList(Predicate<TuitionClass> predicate);

    //=========== Filtered Person List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

}
