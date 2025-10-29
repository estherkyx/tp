package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Category.PARENT;
import static seedu.address.model.person.Category.TUTOR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Contains integration tests and unit tests for GetClassesCommand.
 */
public class GetClassesCommandTest {
    private Model model;
    private Tutor tutor;
    private TuitionClass class1;
    private TuitionClass class2;
    private TuitionClass class3;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        tutor = new Tutor(TUTOR,
                new Name("Reyna Bong"),
                new Phone("98765432"),
                new Email("reyna@example.com"),
                new Address("123 Street"),
                new HashSet<>());
        model.addPerson(tutor);

        PersonId tutorId = tutor.getId();

        class1 = new TuitionClass(Day.MONDAY, Time.H12);
        class1.setTutorId(tutorId);
        model.addTuitionClass(class1);

        class2 = new TuitionClass(Day.WEDNESDAY, Time.H14);
        class2.setTutorId(tutorId);
        model.addTuitionClass(class2);

        class3 = new TuitionClass(Day.FRIDAY, Time.H16);
        model.addTuitionClass(class3);
    }

    @Test
    public void execute_noParameter_listsAllClasses() throws CommandException {
        GetClassesCommand command = new GetClassesCommand();

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("All Tuition Classes"));
        assertTrue(result.getFeedbackToUser().contains("1. Monday 1200"));
        assertTrue(result.getFeedbackToUser().contains("2. Wednesday 1400"));
        assertTrue(result.getFeedbackToUser().contains("3. Friday 1600"));
    }

    @Test
    public void execute_validTutorName_listsTutorClasses() throws CommandException {
        GetClassesCommand command = new GetClassesCommand(new Name("Reyna Bong"));

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Classes taught by Reyna Bong"));
        assertTrue(result.getFeedbackToUser().contains("1. Monday 1200"));
        assertTrue(result.getFeedbackToUser().contains("2. Wednesday 1400"));
        assertFalse(result.getFeedbackToUser().contains("3. Friday 1600"));
    }

    @Test
    public void execute_tutorNameNotFound_throwsCommandException() {
        GetClassesCommand command = new GetClassesCommand(new Name("Nonexistent Tutor"));

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));

        assertEquals(String.format(GetClassesCommand.MESSAGE_TUTOR_NOT_FOUND, new Name("Nonexistent Tutor")),
                exception.getMessage());
    }

    @Test
    public void execute_noClassesForTutor_throwsCommandException() {
        Tutor tutorNoClass = new Tutor(TUTOR,
                new Name("No Class Tutor"),
                new Phone("98765432"),
                new Email("noclasstutor@example.com"),
                new Address("123 Street"),
                new HashSet<>());
        model.addPerson(tutorNoClass);

        GetClassesCommand command = new GetClassesCommand(tutorNoClass.getName());

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));

        assertEquals(String.format(GetClassesCommand.MESSAGE_NO_CLASSES_FOUND, tutorNoClass.getName()),
                exception.getMessage());
    }

    @Test
    public void execute_parentSameNameAsTutor_findsTutorSuccessfully() throws CommandException {
        Parent sameNameParent = new Parent(PARENT,
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("johnd@example.com"),
                new Address("123 Street"),
                new HashSet<>());
        model.addPerson(sameNameParent);

        Tutor sameNameTutor = new Tutor(TUTOR,
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("johnd@example.com"),
                new Address("123 Street"),
                new HashSet<>());
        model.addPerson(sameNameTutor);

        GetClassesCommand command = new GetClassesCommand(new Name("John Doe"));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(GetClassesCommand.MESSAGE_NO_CLASSES_FOUND, new Name("John Doe")),
                exception.getMessage());
    }

    @Test
    public void equals() {
        GetClassesCommand a = new GetClassesCommand(new Name("Reyna Bong"));
        GetClassesCommand b = new GetClassesCommand(new Name("Carol Lim"));

        // same values -> returns true
        assertTrue(a.equals(new GetClassesCommand(new Name("Reyna Bong"))));

        // same object -> returns true
        assertTrue(a.equals(a));

        // null -> returns false
        assertFalse(a.equals(null));

        // different types -> returns false
        assertFalse(a.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(a.equals(b));
    }
}
