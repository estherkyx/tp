package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;

/**
 * Links a student to a parent in the address book.
 */
public class LinkParentCommand extends Command {
    public static final String COMMAND_WORD = "linkParent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": links a Student to a Tutor "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NAME + "Amy Tan";


    public static final String MESSAGE_ARGUMENTS = "Student: %1$s, Parent: %2$s";

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
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, studentName, parentName));
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
