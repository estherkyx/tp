package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GetParentCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;

/**
 * Contains integration tests and unit tests for GetParentCommand.
 */
public class GetParentCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_notImplemented_failure() {
        Name studentName = new Name("John Doe");

        assertCommandFailure(new GetParentCommand(studentName), model, String.format(MESSAGE_ARGUMENTS, studentName));
    }

    @Test
    public void equals() {
        GetParentCommand a = new GetParentCommand(new Name("John Doe"));
        GetParentCommand b = new GetParentCommand(new Name("Carol Lim"));

        // same values -> returns true
        assertTrue(a.equals(new GetParentCommand(new Name("John Doe"))));

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
