package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ClassQueries.getStudentsInClass;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.ClassId;
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

    private final ClassId classId;

    /**
     * Creates a LinkClassCommand to link the specified student to a class.
     */
    public GetClassDetailsCommand(Day day, Time time) {
        requireNonNull(day);
        requireNonNull(time);
        this.classId = new ClassId(day, time);
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<TuitionClass> classOpt = model.findTuitionClass(classId);
        if (classOpt.isEmpty()) {
            throw new CommandException(MESSAGE_CLASS_NOT_FOUND);
        }
        TuitionClass tuitionClass = classOpt.get();
        Optional<Tutor> tutorOptional = model.getAddressBook().getPersonList().stream()
                    .filter(t -> t.getId().equals(tuitionClass.getTutorId()))
                    .filter(p -> p instanceof Tutor)
                    .map(p -> (Tutor) p)
                    .findFirst();
        List<Student> students = getStudentsInClass(model, tuitionClass);
        Set<PersonId> studentIds = students.stream()
                .map(Student::getId)
                .collect(Collectors.toSet());

        StringBuilder sb = new StringBuilder("Class on ").append(tuitionClass.getDay()).append(", ")
                .append(tuitionClass.getTime().toDisplayString()).append("\n");
        sb.append("Tutor: ").append(tutorOptional.map(t -> t.getName().toString()).orElse("None"));
        sb.append("\nStudents:");
        if (students.isEmpty()) {
            sb.append(" None");
        } else {
            String list = students.stream()
                    .map(s -> s.getName().toString())
                    .collect(Collectors.joining(", "));
            sb.append(" ").append(list);
        }

        model.updateFilteredPersonList(p -> studentIds.contains(p.getId())
                || tutorOptional.map(t -> t.getId().equals(p.getId())).orElse(false));


        return new CommandResult(sb.toString());
    }
}
