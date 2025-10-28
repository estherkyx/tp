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

    public static final String MESSAGE_LINK_SUCCESS = "Linked Student %1$s to Parent %2$s";
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
        Optional<Person> studentOpt = personList.stream()
                .filter(p -> p.getName().equals(studentName)).findFirst();
        if (studentOpt.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentName));
        }
        if (!(studentOpt.get() instanceof Student)) {
            throw new CommandException(String.format(MESSAGE_WRONG_PERSON_TYPE, studentName, "Student"));
        }
        Student studentToLink = (Student) studentOpt.get();

        // Find the parent
        Optional<Person> parentOpt = personList.stream()
                .filter(p -> p.getName().equals(parentName)).findFirst();
        if (parentOpt.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, parentName));
        }
        if (!(parentOpt.get() instanceof Parent)) {
            throw new CommandException(String.format(MESSAGE_WRONG_PERSON_TYPE, parentName, "Parent"));
        }
        if (studentToLink.getParentId() != null) {
            // Find old parent
            Optional<Person> oldParentOpt = personList.stream()
                    .filter(p -> p instanceof Parent && p.getId().equals(studentToLink.getParentId()))
                    .findFirst();
            if (oldParentOpt.isPresent()) {
                Parent oldParent = (Parent) oldParentOpt.get();
                oldParent.removeChildId(studentToLink.getId());
                model.setPerson(oldParent, oldParent);
            }
        }
        Parent parentToLink = (Parent) parentOpt.get();

        // Link the student and parent
        studentToLink.setParentId(parentToLink.getId());
        parentToLink.addChildId(studentToLink.getId());

        // Even though the objects are mutated, use setPerson to ensure the UI updates
        model.setPerson(studentOpt.get(), studentToLink);
        model.setPerson(parentOpt.get(), parentToLink);

        return new CommandResult(String.format(MESSAGE_LINK_SUCCESS,
                studentToLink.getName(), parentToLink.getName()));
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
