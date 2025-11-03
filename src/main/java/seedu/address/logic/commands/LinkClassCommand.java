package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.ClassId;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Links a student or tutor to an existing tuition class in the address book.
 */
public class LinkClassCommand extends Command {

    public static final String COMMAND_WORD = "linkClass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a student or tutor to a tuition class. \n"
            + "Parameters: "
            + PREFIX_DAY + "*DAY "
            + PREFIX_TIME + "*TIME "
            + PREFIX_NAME + "*TUTOR_NAME or *STUDENT_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "Monday "
            + PREFIX_TIME + "H14 "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_LINK_STUDENT_SUCCESS = "Linked Student %1$s to Class on %2$s, %3$s";
    public static final String MESSAGE_ASSIGN_TUTOR_SUCCESS = "Assigned Tutor %1$s to Class on %2$s, %3$s";
    public static final String MESSAGE_CLASS_NOT_FOUND = "The class at the specified timeslot does not exist.";
    public static final String MESSAGE_PERSON_NOT_STUDENT_OR_TUTOR = "The person provided is not a student or a tutor.";
    public static final String MESSAGE_STUDENT_ALREADY_LINKED = "The student is already linked to a class.";
    public static final String MESSAGE_TUTOR_ALREADY_TEACHING = "The tutor is already assigned to this class.";
    public static final String MESSAGE_CLASS_ALREADY_HAS_TUTOR =
            "This class is already assigned to Tutor %s. Unlink before reassigning.";

    private final ClassId classId;
    private final Name personName;
    private final Optional<Category> category;

    /**
     * Creates a LinkClassCommand to link the specified person to a class.
     */
    public LinkClassCommand(Day day, Time time, Name personName) {
        requireNonNull(day);
        requireNonNull(time);
        requireNonNull(personName);
        this.classId = new ClassId(day, time);
        this.personName = personName;
        this.category = Optional.empty();
    }

    /**
     * Creates a LinkClassCommand to link the specified student/tutor to a class.
     */
    public LinkClassCommand(Day day, Time time, Name personName, Category category) {
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
        Optional<TuitionClass> classToLinkOpt = model.findTuitionClass(classId);
        if (classToLinkOpt.isEmpty()) {
            throw new CommandException(MESSAGE_CLASS_NOT_FOUND);
        }
        TuitionClass classToLink = classToLinkOpt.get();

        // 2. Find the Person
        List<Person> personsNamed = model.findPersonByName(personName);
        if (category.isPresent()) {
            personsNamed = personsNamed.stream().filter(p -> category.get().equals(p.getCategory())).toList();
        }
        if (personsNamed.isEmpty()) {
            throw new CommandException(Messages.messagePersonNotFound(personName));
        }

        Person personToLink = personsNamed.get(0);

        // 3. Branch logic based on Person type
        if (personToLink instanceof Student) {
            return linkStudent(model, (Student) personToLink, classToLink);
        } else if (personToLink instanceof Tutor) {
            return assignTutor(model, (Tutor) personToLink, classToLink);
        } else {
            throw new CommandException(MESSAGE_PERSON_NOT_STUDENT_OR_TUTOR);
        }
    }

    /**
     * Handles the logic for linking a student.
     */
    private CommandResult linkStudent(Model model, Student student, TuitionClass tuitionClass) throws CommandException {
        // Student must not be in another class
        if (student.getClassId().isPresent()) {
            throw new CommandException(MESSAGE_STUDENT_ALREADY_LINKED);
        }

        // Perform the link
        student.setTuitionClass(tuitionClass);
        tuitionClass.addStudentId(student.getId());
        // Update the tuition class in the model to trigger UI refresh
        model.setTuitionClass(tuitionClass, tuitionClass);
        model.setPerson(student, student);

        return new CommandResult(String.format(MESSAGE_LINK_STUDENT_SUCCESS,
                student.getName(), tuitionClass.getDay(), tuitionClass.getTime().toDisplayString()));
    }

    /**
     * Handles the logic for assigning a tutor.
     */
    private CommandResult assignTutor(Model model, Tutor tutor, TuitionClass tuitionClass) throws CommandException {
        // Get the current tutor ID, which could be null
        PersonId currentTutorId = tuitionClass.getTutorId();

        // Check if the current tutor is not null AND if its ID matches the new tutor's ID.
        if (currentTutorId != null) {
            if (currentTutorId.equals(tutor.getId())) {
                throw new CommandException(MESSAGE_TUTOR_ALREADY_TEACHING);
            }

            String currentTutorName = model.findPersonById(currentTutorId)
                    .map(person -> person.getName().toString())
                    .orElse("an unknown tutor");

            throw new CommandException(String.format(MESSAGE_CLASS_ALREADY_HAS_TUTOR, currentTutorName));
        }
        // Perform the assignment
        tuitionClass.setTutorId(tutor.getId());
        // Update the tuition class in the model to trigger UI refresh
        model.setTuitionClass(tuitionClass, tuitionClass);
        // Update the tutor in the model to trigger UI refresh
        model.setPerson(tutor, tutor);

        return new CommandResult(String.format(MESSAGE_ASSIGN_TUTOR_SUCCESS,
                tutor.getName(), tuitionClass.getDay(), tuitionClass.getTime().toDisplayString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LinkClassCommand)) {
            return false;
        }
        LinkClassCommand otherCommand = (LinkClassCommand) other;
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
