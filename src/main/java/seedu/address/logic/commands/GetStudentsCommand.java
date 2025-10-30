package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Retrieves all students of a specified tutor in the address book.
 */
public class GetStudentsCommand extends Command {

    public static final String COMMAND_WORD = "getStudents";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all students taught by a tutor. \n"
            + "Parameters: n/TUTOR_NAME\n"
            + "Example: " + COMMAND_WORD + " n/Aaron Tan";

    public static final String MESSAGE_SUCCESS = "Listed %d student(s) taught by '%s'.";

    public static final String MESSAGE_TUTOR_NOT_FOUND = "Tutor with name '%s' not found.";

    public static final String MESSAGE_NO_STUDENT_LINKED = "Tutor '%s' has no students.";

    private final Name tutorName;

    /**
     * Creates a command to list students linked to the given tutor name.
     * @param tutorName of the list of students to retrieve
     */
    public GetStudentsCommand(Name tutorName) {
        requireNonNull(tutorName);
        this.tutorName = tutorName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personsNamed = model.findPersonByName(tutorName);
        Tutor tutor = (Tutor) personsNamed.stream()
                .filter(person -> person instanceof Tutor)
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_TUTOR_NOT_FOUND, tutorName)));

        List<TuitionClass> tutorClasses = model.getClassesByTutor(tutor);

        // Tutor has no classes, hence no students
        if (tutorClasses.isEmpty()) {
            model.updateFilteredPersonList(p -> false);
            return new CommandResult(String.format(MESSAGE_NO_STUDENT_LINKED, tutorName));
        }

        Set<PersonId> allStudentIds = tutorClasses.stream()
                .flatMap(c -> model.getStudentsInClass(c).stream())
                .map(p -> p.getId())
                .collect(Collectors.toSet());

        // Tutor's classes have no students
        if (allStudentIds.isEmpty()) {
            model.updateFilteredPersonList(p -> false);
            return new CommandResult(String.format(MESSAGE_NO_STUDENT_LINKED, tutorName));
        }

        // Update UI to show filtered list of students
        model.updateFilteredPersonList(p ->
                p instanceof Student && allStudentIds.contains(p.getId()));
        int shown = model.getFilteredPersonList().size();

        return new CommandResult(String.format(MESSAGE_SUCCESS, shown, tutor.getName()));
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetStudentsCommand)) {
            return false;
        }

        // state check
        GetStudentsCommand e = (GetStudentsCommand) other;
        return tutorName.equals(e.tutorName);
    }
}
