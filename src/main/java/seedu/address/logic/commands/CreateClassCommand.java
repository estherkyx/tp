package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Creates a new tuition class time slot.
 */
public class CreateClassCommand extends Command {

    public static final String COMMAND_WORD = "createClass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new tuition class time slot. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "monday "
            + PREFIX_TIME + "h14";

    public static final String MESSAGE_SUCCESS = "New class created: %1$s, %2$s";
    public static final String MESSAGE_DUPLICATE_CLASS = "This class time slot already exists";

    private final TuitionClass toAdd;

    /**
     * Creates a CreateClassCommand to add a class with the specified day and time.
     */
    public CreateClassCommand(Day day, Time time) {
        requireNonNull(day);
        requireNonNull(time);
        this.toAdd = new TuitionClass(day, time);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTuitionClass(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLASS);
        }

        model.addTuitionClass(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getDay(), toAdd.getTime().toDisplayString()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CreateClassCommand)) {
            return false;
        }
        CreateClassCommand otherCreateClassCommand = (CreateClassCommand) other;
        return toAdd.equals(otherCreateClassCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
