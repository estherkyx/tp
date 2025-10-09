package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;

/**
 * Retrieves the parent of a specified student in the address book.
 */
public class GetParentCommand extends Command {
    public static final String COMMAND_WORD = "getParent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the parent of the given student.\n"
            + "Parameters: n/STUDENT_NAME\n"
            + "Example: " + COMMAND_WORD + " " + " n/John Doe";

    public static final String MESSAGE_ARGUMENTS = "Student: %1$s";

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
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, studentName));
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
