package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.model.person.Category.PARENT;
import static seedu.address.model.person.Category.TUTOR;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TuitionClassBuilder;

/**
 * Contains integration tests and unit tests for GetStudentsCommand.
 */
public class GetStudentsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        AddressBook typical = new AddressBook(getTypicalAddressBook());

        Tutor newGeorge = (Tutor) new PersonBuilder(GEORGE).build();
        typical.setPerson(GEORGE, newGeorge);

        Student newAlice = (Student) new PersonBuilder(ALICE).build();
        Student newBenson = (Student) new PersonBuilder(BENSON).build();
        typical.setPerson(ALICE, newAlice);
        typical.setPerson(BENSON, newBenson);

        TuitionClass c1 = new TuitionClassBuilder().withDay(Day.MONDAY).withTime(Time.H14).withTutor(newGeorge).build();
        TuitionClass c2 = new TuitionClassBuilder().withDay(Day.MONDAY).withTime(Time.H16).withTutor(newGeorge).build();
        typical.addTuitionClass(c1);
        typical.addTuitionClass(c2);

        newAlice.setTuitionClass(c1);
        c1.addStudentId(newAlice.getId());

        newBenson.setTuitionClass(c2);
        c2.addStudentId(newBenson.getId());

        model = new ModelManager(typical, new UserPrefs());
    }

    @Test
    public void execute_tutorExistsWithStudents_filtersAndShowsCount() throws Exception {
        GetStudentsCommand cmd = new GetStudentsCommand(GEORGE.getName());
        CommandResult res = cmd.execute(model);

        ObservableList<Person> shown = model.getFilteredPersonList();
        assertEquals(2, shown.size(), "Expected exactly 2 students");
        assertTrue(shown.stream().allMatch(p ->
                p instanceof Student), "Filtered list must contain only Students");
        assertEquals(String.format(GetStudentsCommand.MESSAGE_SUCCESS, shown.size(), GEORGE.getName()),
                res.getFeedbackToUser());
    }

    @Test
    public void execute_tutorExistsNoStudents_showsNoStudent() throws Exception {
        Tutor newFiona = (Tutor) new PersonBuilder(FIONA).build();
        model.setPerson(FIONA, newFiona);

        TuitionClass fionaClass = new TuitionClassBuilder()
                .withDay(Day.TUESDAY).withTime(Time.H14).withTutor(newFiona)
                .build();
        model.addTuitionClass(fionaClass);

        GetStudentsCommand cmd = new GetStudentsCommand(FIONA.getName());
        CommandResult res = cmd.execute(model);

        ObservableList<Person> shown = model.getFilteredPersonList();
        assertEquals(0, shown.size(), "Expected exactly 0 students");
        assertEquals(String.format(GetStudentsCommand.MESSAGE_NO_STUDENT_LINKED, FIONA.getName()),
                res.getFeedbackToUser());
    }

    @Test
    public void execute_tutorExistsNoClasses_showsNoStudent() throws Exception {
        Tutor newFiona = (Tutor) new PersonBuilder(FIONA).build();
        model.setPerson(FIONA, newFiona);

        GetStudentsCommand cmd = new GetStudentsCommand(FIONA.getName());
        CommandResult res = cmd.execute(model);

        ObservableList<Person> shown = model.getFilteredPersonList();
        assertEquals(0, shown.size(), "Expected exactly 0 students");
        assertEquals(String.format(GetStudentsCommand.MESSAGE_NO_STUDENT_LINKED, FIONA.getName()),
                res.getFeedbackToUser());
    }

    @Test
    public void execute_tutorNotFound_assertCommandFailure() {
        Name ghost = new Name("Ghost Tutor");
        GetStudentsCommand cmd = new GetStudentsCommand(ghost);

        assertCommandFailure(cmd, model,
                String.format(GetStudentsCommand.MESSAGE_TUTOR_NOT_FOUND, ghost));
    }

    @Test
    public void execute_parentSameNameAsTutor_findsTutorSuccessfully() throws CommandException {
        Parent sameNameParent = new Parent(PARENT,
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("johnd@example.com"),
                new Address("123 Street"),
                new HashSet<>());
        this.model.addPerson(sameNameParent);

        Tutor sameNameTutor = new Tutor(TUTOR,
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("johnd@example.com"),
                new Address("123 Street"),
                new HashSet<>());
        this.model.addPerson(sameNameTutor);

        GetStudentsCommand command = new GetStudentsCommand(new Name("John Doe"));
        CommandResult result = command.execute(model);

        ObservableList<Person> shown = model.getFilteredPersonList();
        assertEquals(0, shown.size(), "Expected exactly 0 students");
        assertEquals(String.format(GetStudentsCommand.MESSAGE_NO_STUDENT_LINKED, new Name("John Doe")),
                result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        GetStudentsCommand a = new GetStudentsCommand(new Name("Aaron Tan"));
        GetStudentsCommand b = new GetStudentsCommand(new Name("Becky Lim"));

        // same values -> returns true
        assertTrue(a.equals(new GetStudentsCommand(new Name("Aaron Tan"))));

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
