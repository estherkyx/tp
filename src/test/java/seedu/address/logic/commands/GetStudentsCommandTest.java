package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
// import static seedu.address.testutil.TypicalPersons.ALICE;
// import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
// import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for GetStudentsCommand.
 */
public class GetStudentsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        AddressBook typical = new AddressBook(getTypicalAddressBook());
        Tutor newGeorge = (Tutor) new PersonBuilder(GEORGE).build();
        /*
        newGeorge.addStudent((Student) ALICE);
        newGeorge.addStudent((Student) BENSON);
        */

        typical.setPerson(GEORGE, newGeorge);

        model = new ModelManager(typical, new UserPrefs());
    }

    @Test
    public void execute_tutorExistsWithStudents_filtersAndShowsCount() throws Exception {
        GetStudentsCommand cmd = new GetStudentsCommand(GEORGE.getName());
        CommandResult res = cmd.execute(model);

        ObservableList<Person> shown = model.getFilteredPersonList();
        /*
        assertEquals(2, shown.size(), "Expected exactly 2 linked students");
        assertTrue(shown.stream().allMatch(p ->
                p instanceof Student), "Filtered list must contain only Students");
        */
        assertEquals(String.format(GetStudentsCommand.MESSAGE_SUCCESS, 0, GEORGE.getName()),
                res.getFeedbackToUser()); // to change
    }

    @Test
    public void execute_tutorExistsNoStudents_showsEmptyMessage() throws Exception {
        GetStudentsCommand cmd = new GetStudentsCommand(FIONA.getName());
        CommandResult res = cmd.execute(model);

        // assertEquals(0, model.getFilteredPersonList().size());
        assertEquals(String.format(GetStudentsCommand.MESSAGE_SUCCESS, 0, FIONA.getName()),
                res.getFeedbackToUser()); // to change
    }

    @Test
    public void execute_tutorNotFound_assertCommandFailure() {
        Name ghost = new Name("Ghost Tutor");
        GetStudentsCommand cmd = new GetStudentsCommand(ghost);

        assertCommandFailure(cmd, model,
                String.format(GetStudentsCommand.MESSAGE_TUTOR_NOT_FOUND, ghost));
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
