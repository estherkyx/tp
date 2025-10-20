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

    public boolean contains(TuitionClass toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    public void add(TuitionClass toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTuitionClassException();
        }
        internalList.add(toAdd);
    }

    public void setTuitionClasses(List<TuitionClass> tuitionClasses) {
        requireAllNonNull(tuitionClasses);
        if (!tuitionClassesAreUnique(tuitionClasses)) {
            throw new DuplicateTuitionClassException();
        }
        internalList.setAll(tuitionClasses);
    }

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