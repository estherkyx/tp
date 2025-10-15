package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;

/**
 * Retrieves the parent of a specified student in the address book.
 */
public class GetParentCommand extends Command {
    public static final String COMMAND_WORD = "getParent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the parent of the given student.\n"
            + "Parameters: n/STUDENT_NAME\n"
            + "Example: " + COMMAND_WORD + " " + " n/John Doe";

    public static final String MESSAGE_SUCCESS = "Parent of %s:\n%s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student with name '%s' not found.";
    public static final String MESSAGE_NO_PARENT_LINKED = "Student '%s' has no linked parent.";

    private final Name studentName;

    /**
     * Creates a command to retrieve the parent of the given student name.
     * @param studentName of the parent to retrieve.
     */
    public GetParentCommand(Name studentName) {
        requireNonNull(studentName);
        this.studentName = studentName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        // Find the student with the matching name
        Person targetStudent = null;
        for (Person person : lastShownList) {
            if (person.getCategory().equals("student") && person.getName().equals(studentName)) {
                targetStudent = person;
                break;
            }
        }

        // If student is not found, throw an exception
        if (targetStudent == null) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentName));
        }

        // Get the parent ID from the student
        PersonId parentId = targetStudent.getParentId();

        // If student has no linked parent, throw exception
        if (parentId == null) {
            throw new CommandException(String.format(MESSAGE_NO_PARENT_LINKED, studentName));
        }

        // Find parent with the matching ID
        Person targetParent = null;
        for (Person person : lastShownList) {
            if (person.getCategory().equals("parent") && person.getId().equals(parentId)) {
                targetParent = person;
                break;
            }
        }

        // Return parent details
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentName, targetParent));
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetParentCommand)) {
            return false;
        }

        // state check
        GetParentCommand e = (GetParentCommand) other;
        return studentName.equals(e.studentName);
    }
}
