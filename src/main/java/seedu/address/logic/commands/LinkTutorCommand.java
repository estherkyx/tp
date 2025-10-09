package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

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

    public static final String MESSAGE_ARGUMENTS = "Student: %1$s, Parent %2$s";

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
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, studentName, tutorName));
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
