package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Category;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.ClassId;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<TuitionClass> filteredTuitionClasses;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTuitionClasses = new FilteredList<>(this.addressBook.getTuitionClassList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        requireNonNull(target);

        // Unlink parent from students if target is a parent
        if (target instanceof Parent) {
            unlinkParentFromStudent(target.getId());
        }

        // Unlink student from parent if target is a student
        if (target instanceof Student) {
            unlinkStudentFromParent(target.getId());
            unlinkStudentFromClasses(target.getId());
        }

        // Unlink tutor from classes if target is a tutor
        if (target instanceof Tutor) {
            unlinkTutorFromClasses(target.getId());
        }

        addressBook.removePerson(target);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addParent(Parent parent) {
        addressBook.addParent(parent);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addTutor(Tutor tutor) {
        addressBook.addTutor(tutor);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addStudent(Student student) {
        addressBook.addStudent(student);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public List<Person> findPersonByName(Name name) {
        requireNonNull(name);
        return addressBook.getPersonList().stream()
                .filter(person -> person.getName().toString().trim().replaceAll("\\s+", " ")
                        .equalsIgnoreCase(name.toString().trim().replaceAll("\\s+", " ")))
                .toList();
    }

    @Override
    public Optional<Person> findPersonById(PersonId id) {
        requireNonNull(id);
        return addressBook.getPersonList().stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public Set<PersonId> getPersonIdsByCategory(Category category) {
        return addressBook.getPersonList().stream()
                .filter(p -> p.getCategory() == category)
                .map(p -> p.getId())
                .collect(Collectors.toSet());
    }

    @Override
    public void unlinkParentFromStudent(PersonId parentId) {
        requireNonNull(parentId);

        List<Person> allPersons = addressBook.getPersonList();
        for (Person person : allPersons) {
            if (person instanceof Student) {
                Student student = (Student) person;
                if (parentId.equals(student.getParentId())) {
                    student.clearParent();
                    break;
                }
            }
        }
    }

    @Override
    public void unlinkStudentFromParent(PersonId studentId) {
        requireNonNull(studentId);
        List<Person> allPersons = addressBook.getPersonList();

        for (Person person : allPersons) {
            if (person instanceof Parent) {
                Parent parent = (Parent) person;
                Set<PersonId> children = parent.getChildrenIds();
                if (children.contains(studentId)) {
                    parent.removeChildId(studentId);
                    addressBook.setPerson(parent, parent);
                }
            }
        }
    }

    @Override
    public void unlinkTutorFromClasses(PersonId tutorId) {
        requireNonNull(tutorId);
        List<TuitionClass> allClasses = addressBook.getTuitionClassList();

        for (TuitionClass tuitionClass : allClasses) {
            if (tutorId.equals(tuitionClass.getTutorId())) {
                tuitionClass.removeTutorId();
            }
        }
    }

    @Override
    public void unlinkStudentFromClasses(PersonId studentId) {
        requireNonNull(studentId);
        List<TuitionClass> allClasses = addressBook.getTuitionClassList();

        for (TuitionClass tuitionClass : allClasses) {
            if (tuitionClass.getStudentIds().contains(studentId)) {
                tuitionClass.removeStudentId(studentId);
                break;
            }
        }
    }

    //=========== TuitionClass =====================================================================

    @Override
    public boolean hasTuitionClass(TuitionClass tuitionClass) {
        requireNonNull(tuitionClass);
        return addressBook.hasTuitionClass(tuitionClass);
    }

    @Override
    public void addTuitionClass(TuitionClass tuitionClass) {
        addressBook.addTuitionClass(tuitionClass);
        updateFilteredTuitionClassList(PREDICATE_SHOW_ALL_CLASSES);
    }

    @Override
    public void setTuitionClass(TuitionClass target, TuitionClass editedTuitionClass) {
        requireNonNull(editedTuitionClass);
        addressBook.setTuitionClass(target, editedTuitionClass);
    }

    @Override
    public Optional<TuitionClass> findTuitionClass(ClassId classId) {
        requireNonNull(classId);
        return addressBook.getTuitionClassList().stream()
                .filter(tuitionClass -> tuitionClass.getClassId().equals(classId))
                .findFirst();
    }

    @Override
    public ObservableList<TuitionClass> getTuitionClassList() {
        return addressBook.getTuitionClassList();
    }

    @Override
    public List<TuitionClass> getClassesByTutor(Tutor tutor) {
        return getTuitionClassList().stream()
                .filter(tuitionClass -> tutor.getId().equals(tuitionClass.getTutorId()))
                .toList();
    }

    @Override
    public List<Student> getStudentsInClass(TuitionClass tuitionClass) {
        return getAddressBook().getPersonList().stream()
                .filter(t -> tuitionClass.getStudentIds().contains(t.getId()))
                .filter(p -> p instanceof Student)
                .map(t -> (Student) t)
                .sorted(Comparator.comparing(a -> a.getName().toString()))
                .toList();
    }

    //=========== Filtered Tuition Class List Accessors ============================================

    @Override
    public ObservableList<TuitionClass> getFilteredTuitionClassList() {
        return filteredTuitionClasses;
    }

    @Override
    public void updateFilteredTuitionClassList(Predicate<TuitionClass> predicate) {
        requireNonNull(predicate);
        filteredTuitionClasses.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredTuitionClasses.equals(otherModelManager.filteredTuitionClasses);
    }

}
