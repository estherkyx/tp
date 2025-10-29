package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Links a student to a parent in the address book.
 */
public class LinkParentCommand extends Command {
    public static final String COMMAND_WORD = "linkParent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": links a student to a parent by their names "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NAME + "Amy Tan";

    public static final String MESSAGE_LINK_SUCCESS = "Linked Student %1$s to Parent %2$s.";
    public static final String MESSAGE_LINK_SAME_PARENT = "Parent %1$s is already linked to Student %2$s.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person with name %s could not be found.";
    public static final String MESSAGE_WRONG_PERSON_TYPE = "The person %s is not a %s.";

    private final Name studentName;
    private final Name parentName;

    /**
     * @param studentName of the student to be linked
     * @param parentName of the parent to be linked
     */
    public LinkParentCommand(Name studentName, Name parentName) {
        requireAllNonNull(studentName, parentName);
        this.studentName = studentName;
        this.parentName = parentName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Person> personList = model.getAddressBook().getPersonList();

        // Find the student
        List<Person> personsNamedStudent = model.findPersonByName(studentName);
        if (personsNamedStudent.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentName));
        }
        boolean namedNotStudent = personsNamedStudent.stream().noneMatch(p -> p instanceof Student);
        if (namedNotStudent) {
            throw new CommandException(String.format(MESSAGE_WRONG_PERSON_TYPE, studentName, "Student"));
        }
        Student student = (Student) personsNamedStudent.stream()
                .filter(p -> p instanceof Student)
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentName)));

        // Find the parent
        List<Person> personsNamedParent = model.findPersonByName(parentName);
        if (personsNamedParent.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, parentName));
        }
        boolean namedNotParent = personsNamedParent.stream().noneMatch(p -> p instanceof Parent);
        if (namedNotParent) {
            throw new CommandException(String.format(MESSAGE_WRONG_PERSON_TYPE, parentName, "Parent"));
        }
        Parent parent = (Parent) personsNamedParent.stream()
                .filter(p -> p instanceof Parent)
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, parentName)));

        // Find the old parent, if any
        if (student.getParentId() != null) {
            Optional<Person> oldParentOpt = personList.stream()
                    .filter(p -> p instanceof Parent && p.getId().equals(student.getParentId()))
                    .findFirst();
            if (oldParentOpt.isPresent() && oldParentOpt.get().equals(parent)) {
                throw new CommandException(String.format(MESSAGE_LINK_SAME_PARENT, parentName, studentName));
            }
            Parent oldParent = (Parent) oldParentOpt.get();
            oldParent.removeChildId(student.getId());
            model.setPerson(oldParent, oldParent);
        }

        // Link the student and parent
        student.setParentId(parent.getId());
        parent.addChildId(student.getId());

        // Even though the objects are mutated, use setPerson to ensure the UI updates
        model.setPerson(student, student);
        model.setPerson(parent, parent);

        return new CommandResult(String.format(MESSAGE_LINK_SUCCESS,
                student.getName(), parent.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LinkParentCommand)) {
            return false;
        }

        LinkParentCommand e = (LinkParentCommand) other;
        return studentName.equals(e.studentName)
                && parentName.equals(e.parentName);
    }
}
