package seedu.address.model.tuitionclass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tuitionclass.exceptions.DuplicateTuitionClassException;

/**
 * A list of tuition classes that enforces uniqueness between its elements.
 * A class is considered unique by its timeslot (day and time).
 */
public class UniqueClassList implements Iterable<TuitionClass> {

    private final ObservableList<TuitionClass> internalList = FXCollections.observableArrayList();
    private final ObservableList<TuitionClass> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tuition class as the given argument.
     *
     * @param toCheck The tuition class to check for existence in the list. Must not be null.
     * @return True if an equivalent tuition class is found, false otherwise.
     */
    public boolean contains(TuitionClass toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a tuition class to the list.
     * The tuition class must not already exist in the list.
     *
     * @param toAdd The tuition class to add. Must not be null.
     * @throws DuplicateTuitionClassException If the tuition class to add is a duplicate of an existing tuition class.
     */
    public void add(TuitionClass toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTuitionClassException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the tuition class {@code target} in the list with {@code editedTuitionClass}.
     * {@code target} must exist in the list.
     * The tuition class identity of {@code editedTuitionClass} must not be the same as another existing
     * tuition class in the list.
     *
     * @param target The tuition class to replace. Must not be null.
     * @param editedTuitionClass The replacement tuition class. Must not be null.
     * @throws DuplicateTuitionClassException If {@code editedTuitionClass} is a duplicate of an existing
     *         tuition class.
     */
    public void setTuitionClass(TuitionClass target, TuitionClass editedTuitionClass) {
        requireNonNull(editedTuitionClass);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RuntimeException("Target tuition class not found in list");
        }

        // Check if the edited tuition class would create a duplicate (excluding the target itself)
        for (int i = 0; i < internalList.size(); i++) {
            if (i != index && internalList.get(i).equals(editedTuitionClass)) {
                throw new DuplicateTuitionClassException();
            }
        }

        internalList.set(index, editedTuitionClass);
    }

    /**
     * Replaces the tuition classes in the list with {@code tuitionClasses}.
     * {@code tuitionClasses} must not contain duplicate tuition classes.
     *
     * @param tuitionClasses The list of tuition classes to set. Must not be null and must not contain duplicates.
     * @throws DuplicateTuitionClassException If {@code tuitionClasses} contains duplicate tuition classes.
     */
    public void setTuitionClasses(List<TuitionClass> tuitionClasses) {
        requireAllNonNull(tuitionClasses);
        if (!tuitionClassesAreUnique(tuitionClasses)) {
            throw new DuplicateTuitionClassException();
        }
        internalList.setAll(tuitionClasses);
    }

    /**
     * Returns the internal list as an unmodifiable {@code ObservableList}.
     *
     * @return An unmodifiable view of the tuition classes.
     */
    public ObservableList<TuitionClass> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<TuitionClass> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueClassList
                && internalList.equals(((UniqueClassList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean tuitionClassesAreUnique(List<TuitionClass> tuitionClasses) {
        for (int i = 0; i < tuitionClasses.size() - 1; i++) {
            for (int j = i + 1; j < tuitionClasses.size(); j++) {
                if (tuitionClasses.get(i).equals(tuitionClasses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
