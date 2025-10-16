package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LinkParentCommand.
 */
public class LinkParentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentAndParent_success() {
        Student studentToLink = (Student) ALICE;
        Parent parentToLink = (Parent) DANIEL;

        LinkParentCommand linkParentCommand = new LinkParentCommand(studentToLink.getName(), parentToLink.getName());

        String expectedMessage = String.format(LinkParentCommand.MESSAGE_LINK_SUCCESS,
                Messages.format(studentToLink), Messages.format(parentToLink));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Student expectedStudent = (Student) expectedModel.getFilteredPersonList().stream()
                .filter(p -> p.equals(studentToLink)).findFirst().get();
        Parent expectedParent = (Parent) expectedModel.getFilteredPersonList().stream()
                .filter(p -> p.equals(parentToLink)).findFirst().get();

        expectedStudent.setParentId(expectedParent.getId());
        expectedParent.addChildId(expectedStudent.getId());

        assertCommandSuccess(linkParentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        Name nonExistentStudentName = new Name("NonExistent Student");
        LinkParentCommand command = new LinkParentCommand(nonExistentStudentName, FIONA.getName());

        assertCommandFailure(command, model, String.format(LinkParentCommand.MESSAGE_PERSON_NOT_FOUND,
                nonExistentStudentName));
    }

    @Test
    public void execute_personNotStudent_throwsCommandException() {
        LinkParentCommand command = new LinkParentCommand(FIONA.getName(), FIONA.getName());

        assertCommandFailure(command, model, String.format(LinkParentCommand.MESSAGE_WRONG_PERSON_TYPE,
                FIONA.getName(), "Student"));
    }

    @Test
    public void execute_personNotParent_throwsCommandException() {
        LinkParentCommand command = new LinkParentCommand(BENSON.getName(), ALICE.getName());

        assertCommandFailure(command, model, String.format(LinkParentCommand.MESSAGE_WRONG_PERSON_TYPE,
                ALICE.getName(), "Parent"));
    }

    @Test
    public void equals() {
        LinkParentCommand linkAliceToFiona = new LinkParentCommand(ALICE.getName(), FIONA.getName());
        LinkParentCommand linkBensonToFiona = new LinkParentCommand(BENSON.getName(), FIONA.getName());

        // same object -> returns true
        assertTrue(linkAliceToFiona.equals(linkAliceToFiona));

        // same values -> returns true
        LinkParentCommand linkAliceToFionaCopy = new LinkParentCommand(ALICE.getName(), FIONA.getName());
        assertTrue(linkAliceToFiona.equals(linkAliceToFionaCopy));

        // different types -> returns false
        assertFalse(linkAliceToFiona.equals(1));

        // null -> returns false
        assertFalse(linkAliceToFiona.equals(null));

        // different student -> returns false
        assertFalse(linkAliceToFiona.equals(linkBensonToFiona));
    }
}
