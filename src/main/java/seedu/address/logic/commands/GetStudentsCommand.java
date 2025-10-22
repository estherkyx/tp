package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
// import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
// import seedu.address.model.person.PersonId;
// import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Retrieves all students of a specified tutor in the address book.
 */
public class GetStudentsCommand extends Command {

    public static final String COMMAND_WORD = "getStudents";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all students linked to a tutor.\n"
            + "Parameters: n/TUTOR_NAME\n"
            + "Example: " + COMMAND_WORD + " n/Aaron Tan";

    public static final String MESSAGE_SUCCESS = "Listed %d student(s) linked to %s.";

    public static final String MESSAGE_TUTOR_NOT_FOUND = "Tutor with name '%s' not found.";

    public static final String MESSAGE_NO_STUDENT_LINKED = "Tutor '%s' has no linked student.";

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

        Tutor tutor = findTutorByName(model, tutorName)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_TUTOR_NOT_FOUND, tutorName)));

        // To be updated with getClasses and getClassDetails

        /*
        Set<PersonId> linkedStudentIds = tutor.getStudentsIds();

        // Tutor has no linked students
        if (linkedStudentIds.isEmpty()) {
            model.updateFilteredPersonList(p -> false);
            return new CommandResult(String.format(MESSAGE_NO_STUDENT_LINKED, tutor.getName()));
        }

        // Update UI to show filtered list of students
        model.updateFilteredPersonList(p ->
                p instanceof Student && linkedStudentIds.contains(p.getId()));
        int shown = model.getFilteredPersonList().size();
        */

        return new CommandResult(String.format(MESSAGE_SUCCESS, 0, tutor.getName()));
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

    private Optional<Tutor> findTutorByName(Model model, Name name) {
        return model.getAddressBook().getPersonList().stream()
                .filter(p -> p instanceof Tutor)
                .map(p -> (Tutor) p)
                .filter(t -> t.getName().equals(name))
                .findFirst();
    }
}
