package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Prints out the details of a tuition class.
 */
public class GetClassDetailsCommand extends Command {

    public static final String COMMAND_WORD = "getClassDetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": given the day and time of a class, displays the tutor and students of that class. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "Monday "
            + PREFIX_TIME + "H14";

    public static final String MESSAGE_CLASS_NOT_FOUND = "There is no class at the specified day and time";

    private final Day day;
    private final Time time;

    /**
     * Creates a LinkClassCommand to link the specified student to a class.
     */
    public GetClassDetailsCommand(Day day, Time time) {
        requireNonNull(day);
        requireNonNull(time);
        this.day = day;
        this.time = time;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<TuitionClass> classOpt = model.findTuitionClass(day, time);
        if (classOpt.isEmpty()) {
            throw new CommandException(MESSAGE_CLASS_NOT_FOUND);
        }
        TuitionClass tuitionClass = classOpt.get();
        Tutor tutor = (Tutor) model.getAddressBook().getPersonList().stream()
                    .filter(t -> t.getId().equals(tuitionClass.getTutorId()))
                    .findFirst().orElse(null);
        List<Student> students = model.getAddressBook().getPersonList().stream()
                .filter(t -> tuitionClass.getStudentIds().contains(t.getId()))
                .map(t -> (Student) t)
                .toList();

        StringBuilder sb = new StringBuilder("Class on ").append(day).append(", ").append(time).append("\n");
        sb.append("Tutor: ");
        if (isNull(tutor)) {
            sb.append("None");
        } else {
            sb.append(tutor.getName());
        }
        sb.append("\nStudents:");
        if (students.isEmpty()) {
            sb.append(" None ");
        }
        for (Student s : students) {
            sb.append(" ").append(s.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        return new CommandResult(sb.toString());
    }
}
