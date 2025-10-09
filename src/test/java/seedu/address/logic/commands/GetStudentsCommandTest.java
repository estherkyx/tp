package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GetStudentsCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;

/**
 * Contains integration tests and unit tests for RemarkCommand.
 */
public class GetStudentsCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_notImplemented_failure() {
        Name tutorName = new Name("Aaron Tan");

        assertCommandFailure(new GetStudentsCommand(tutorName), model, String.format(MESSAGE_ARGUMENTS, tutorName));
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
