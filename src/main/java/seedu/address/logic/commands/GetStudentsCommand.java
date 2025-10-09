package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;

/**
 * Retrieves all students of a specified tutor in the address book.
 */
public class GetStudentsCommand extends Command {

    public static final String COMMAND_WORD = "getStudents";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all students linked to a tutor.\n"
            + "Parameters: n/TUTOR_NAME\n"
            + "Example: " + COMMAND_WORD + " n/Aaron Tan";

    public static final String MESSAGE_ARGUMENTS = "Tutor: %1$s";

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
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, tutorName));
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
