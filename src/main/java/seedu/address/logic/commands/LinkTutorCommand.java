package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Links a student to a tutor using it's displayed index from the address book.
 */
public class LinkTutorCommand extends Command {
    public static final String COMMAND_WORD = "linkTutor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a student to a tutor. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NAME + "Ze Kai";

    public static final String MESSAGE_ARGUMENTS = "Student: %1$s, Parent: %2$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "No student found by the name %1$s";
    public static final String MESSAGE_TUTOR_NOT_FOUND = "No tutor found by the name %1$s";
    public static final String MESSAGE_SUCCESS = "Student %1$s linked to tutor %2$s!";

    private final Name studentName;
    private final Name tutorName;


    /**
     * @param studentName of the student to be linked
     * @param tutorName of the tutor to be linked to
     */
    public LinkTutorCommand(Name studentName, Name tutorName) {
        requireAllNonNull(studentName, tutorName);
        this.studentName = studentName;
        this.tutorName = tutorName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Student student = model.getAddressBook().getPersonList().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .filter(t -> t.getName().equals(studentName))
                .findFirst().orElseThrow(() -> new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentName)));

        Tutor tutor = model.getAddressBook().getPersonList().stream()
                .filter(p -> p instanceof Tutor)
                .map(p -> (Tutor) p)
                .filter(t -> t.getName().equals(tutorName))
                .findFirst().orElseThrow(() -> new CommandException(String.format(MESSAGE_TUTOR_NOT_FOUND, tutorName)));

        student.setTutor(tutor);
        tutor.addStudent(student);

        return new CommandResult(String.format(MESSAGE_SUCCESS, student.getName(), tutor.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkTutorCommand e)) {
            return false;
        }

        return studentName.equals(e.studentName) && tutorName.equals(e.tutorName);
    }
}
