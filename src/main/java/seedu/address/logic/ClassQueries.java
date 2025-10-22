package seedu.address.logic;

import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tuitionclass.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class ClassQueries {
    private ClassQueries() {}

    public static Optional<Tutor> findTutorByName(Model model, Name name) {
        return model.getAddressBook().getPersonList().stream()
                .filter(p -> p instanceof Tutor)
                .map(p -> (Tutor) p)
                .filter(t -> t.getName().equals(name))
                .findFirst();
    }

    public static List<TuitionClass> getClassesByTutor(Model model, Tutor tutor) {
        return model.getTuitionClassList().stream()
                .filter(tuitionClass -> tutor.getId().equals(tuitionClass.getTutorId()))
                .toList();
    }

    public static List<Student> getStudentsInClass(Model model, TuitionClass tuitionClass) {
        return model.getAddressBook().getPersonList().stream()
                .filter(t -> tuitionClass.getStudentIds().contains(t.getId()))
                .filter(p -> p instanceof Student)
                .map(t -> (Student) t)
                .sorted(Comparator.comparing(a -> a.getName().toString()))
                .toList();
    }
}
