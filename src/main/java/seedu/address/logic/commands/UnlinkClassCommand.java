package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Category;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.ClassId;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Unlinks a student or tutor from an existing tuition class in the address book.
 */
public class UnlinkClassCommand extends Command {

    public static final String COMMAND_WORD = "unlinkClass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlinks a student or tutor from a tuition class. \n"
            + "Parameters: "
            + PREFIX_DAY + "*DAY "
            + PREFIX_TIME + "*TIME "
            + PREFIX_NAME + "*NAME ["
            + PREFIX_CATEGORY + "*CATEGORY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "Monday "
            + PREFIX_TIME + "H14 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_CATEGORY + "student";

    public static final String MESSAGE_CLASS_NOT_FOUND = "The class at the specified timeslot does not exist.";
    public static final String MESSAGE_PERSON_NOT_STUDENT_OR_TUTOR = "The person provided is not a student or a tutor.";
    public static final String MESSAGE_STUDENT_NOT_IN_CLASS = "The student is not linked to this class";
    public static final String MESSAGE_UNLINK_STUDENT_SUCCESS = "Unlinked Student %1$s from Class on %2$s, %3$s";
    public static final String MESSAGE_TUTOR_NOT_TEACHING_THIS_CLASS = "The tutor is not assigned to this class";
    public static final String MESSAGE_UNASSIGN_TUTOR_SUCCESS = "Unassigned Tutor %1$s from Class on %2$s, %3$s";
    public static final String MESSAGE_CLASS_HAS_NO_TUTOR = "This class has no tutor assigned";

    private final ClassId classId;
    private final Name personName;
    private final Optional<Category> category;

    /**
     * Creates an UnlinkClassCommand to unlink the specified person from a class.
     *
     * @param day the day of the class
     * @param time the time of the class
     * @param personName the name of the person
     */
    public UnlinkClassCommand(Day day, Time time, Name personName) {
        requireNonNull(day);
        requireNonNull(time);
        requireNonNull(personName);

        this.classId = new ClassId(day, time);
        this.personName = personName;
        this.category = Optional.empty();
    }

    /**
     * Creates an UnlinkClassCommand to unlink the specified student/tutor from a class.
     *
     * @param day the day of the class
     * @param time the time of the class
     * @param personName the name of the student/tutor
     * @param category the category of the person (student/tutor)
     */
    public UnlinkClassCommand(Day day, Time time, Name personName, Category category) {
        requireNonNull(day);
        requireNonNull(time);
        requireNonNull(personName);
        requireNonNull(category);

        this.classId = new ClassId(day, time);
        this.personName = personName;
        this.category = Optional.of(category);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // 1. Find the Tuition Class
        Optional<TuitionClass> classToUnlinkOpt = model.findTuitionClass(classId);
        if (classToUnlinkOpt.isEmpty()) {
            throw new CommandException(MESSAGE_CLASS_NOT_FOUND);
        }
        TuitionClass classToUnlink = classToUnlinkOpt.get();

        // 2. Find the Person
        List<Person> personsNamed = model.findPersonByName(personName);
        if (category.isPresent()) {
            personsNamed = personsNamed.stream().filter(p -> category.get().equals(p.getCategory())).toList();
        }
        if (personsNamed.isEmpty()) {
            throw new CommandException(Messages.messagePersonNotFound(personName));
        }

        Person personToUnlink = personsNamed.get(0);

        // 3. Branch logic based on Person type
        if (personToUnlink instanceof Student) {
            return unlinkStudent(model, (Student) personToUnlink, classToUnlink);
        } else if (personToUnlink instanceof Tutor) {
            return unassignTutor(model, (Tutor) personToUnlink, classToUnlink);
        } else {
            throw new CommandException(MESSAGE_PERSON_NOT_STUDENT_OR_TUTOR);
        }
    }

    /**
     * Handles the logic for unlinking a student
     */
    private CommandResult unlinkStudent(Model model, Student student, TuitionClass tuitionClass)
            throws CommandException {
        if (!tuitionClass.getStudentIds().contains(student.getId())) {
            throw new CommandException(MESSAGE_STUDENT_NOT_IN_CLASS);
        }
        student.clearTuitionClass();
        tuitionClass.removeStudentId(student.getId());
        // Update the tuition class in the model to trigger UI refresh
        model.setTuitionClass(tuitionClass, tuitionClass);
        model.setPerson(student, student);

        return new CommandResult(String.format(MESSAGE_UNLINK_STUDENT_SUCCESS,
                student.getName(), tuitionClass.getDay(), tuitionClass.getTime().toDisplayString()));
    }

    /**
     * Handles the logic for unassigning a tutor.
     */
    private CommandResult unassignTutor(Model model, Tutor tutor, TuitionClass tuitionClass) throws CommandException {
        if (isNull(tuitionClass.getTutorId())) {
            throw new CommandException(MESSAGE_CLASS_HAS_NO_TUTOR);
        } else if (!tuitionClass.getTutorId().equals(tutor.getId())) {
            throw new CommandException(MESSAGE_TUTOR_NOT_TEACHING_THIS_CLASS);
        }
        tuitionClass.removeTutorId();
        // Update the tuition class in the model to trigger UI refresh
        model.setTuitionClass(tuitionClass, tuitionClass);
        // Update the tutor in the model to trigger UI refresh
        model.setPerson(tutor, tutor);

        return new CommandResult(String.format(MESSAGE_UNASSIGN_TUTOR_SUCCESS,
                tutor.getName(), tuitionClass.getDay(), tuitionClass.getTime().toDisplayString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnlinkClassCommand otherCommand)) {
            return false;
        }
        return classId.equals(otherCommand.classId)
                && personName.equals(otherCommand.personName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("classId", classId)
                .add("personName", personName)
                .toString();
    }
}
