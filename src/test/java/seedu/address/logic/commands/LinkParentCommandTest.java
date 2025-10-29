package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
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
    public void execute_validStudentAndParent_success() throws CommandException {
        Student studentToLink = (Student) CARL;
        Parent parentToLink = (Parent) ELLE;

        LinkParentCommand linkParentCommand = new LinkParentCommand(studentToLink.getName(), parentToLink.getName());

        String expectedMessage = String.format(LinkParentCommand.MESSAGE_LINK_SUCCESS,
                studentToLink.getName(), parentToLink.getName());

        CommandResult result = linkParentCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());

        Student updatedStudent = (Student) model.getFilteredPersonList().stream()
                .filter(p -> p instanceof Student && p.getId().equals(studentToLink.getId()))
                .findFirst().orElseThrow();
        Parent updatedParent = (Parent) model.getFilteredPersonList().stream()
                .filter(p -> p instanceof Parent && p.getId().equals(parentToLink.getId()))
                .findFirst().orElseThrow();;

        assertEquals(updatedParent.getId(), updatedStudent.getParentId());
        assertTrue(updatedParent.getChildrenIds().contains(updatedStudent.getId()));
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
    public void execute_relinkStudentToNewParent_unlinksFromOldParentAndLinksToNew() throws CommandException {
        Student student = (Student) ALICE;
        Parent oldParent = (Parent) DANIEL;
        Parent newParent = (Parent) ELLE;

        LinkParentCommand linkToOldParent = new LinkParentCommand(student.getName(), oldParent.getName());
        linkToOldParent.execute(model);

        LinkParentCommand relinkToNewParent = new LinkParentCommand(student.getName(), newParent.getName());
        relinkToNewParent.execute(model);

        Student updatedStudent = (Student) model.getFilteredPersonList().stream()
                .filter(p -> p.equals(student)).findFirst().get();
        Parent updatedOldParent = (Parent) model.getFilteredPersonList().stream()
                .filter(p -> p.equals(oldParent)).findFirst().get();
        Parent updatedNewParent = (Parent) model.getFilteredPersonList().stream()
                .filter(p -> p.equals(newParent)).findFirst().get();

        assertEquals(updatedStudent.getParentId(), updatedNewParent.getId());
        assertFalse(updatedOldParent.getChildrenIds().contains(updatedStudent.getId()));
        assertTrue(updatedNewParent.getChildrenIds().contains(updatedStudent.getId()));
    }

    @Test
    public void execute_parentAndStudentAlreadyLinked_throwsCommandException() throws CommandException {
        Student student = (Student) ALICE;
        Parent parent = (Parent) DANIEL;

        LinkParentCommand linkToParent = new LinkParentCommand(student.getName(), parent.getName());
        linkToParent.execute(model);

        assertCommandFailure(linkToParent, model, String.format(LinkParentCommand.MESSAGE_LINK_SAME_PARENT,
                parent.getName(), student.getName()));
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
