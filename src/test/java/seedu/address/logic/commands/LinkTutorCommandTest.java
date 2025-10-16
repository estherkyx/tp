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
import seedu.address.model.person.Tutor;
import seedu.address.model.person.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LinkTutorCommand.
 */
public class LinkTutorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentAndTutor_success() {
        Student studentToLink = (Student) ALICE;
        Tutor tutorToLink = (Tutor) FIONA;

        LinkTutorCommand linkTutorCommand = new LinkTutorCommand(studentToLink.getName(), tutorToLink.getName());

        String expectedMessage = String.format(LinkTutorCommand.MESSAGE_SUCCESS,
                Messages.format(studentToLink), Messages.format(tutorToLink));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Student expectedStudent = (Student) expectedModel.getFilteredPersonList().stream()
                .filter(p -> p.equals(studentToLink)).findFirst().get();
        Tutor expectedTutor = (Tutor) expectedModel.getFilteredPersonList().stream()
                .filter(p -> p.equals(tutorToLink)).findFirst().get();

        expectedStudent.setTutorId(expectedTutor.getId());
        expectedTutor.addStudentId(expectedStudent.getId());

        assertCommandSuccess(linkTutorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        Name nonExistentStudentName = new Name("NonExistent Student");
        LinkTutorCommand command = new LinkTutorCommand(nonExistentStudentName, DANIEL.getName());

        assertCommandFailure(command, model, String.format(LinkTutorCommand.MESSAGE_STUDENT_NOT_FOUND,
                nonExistentStudentName));
    }

    @Test
    public void execute_tutorNotFound_throwsCommandException() {
        Name nonExistentTutorName = new Name("NonExistent Tutor");
        LinkTutorCommand command = new LinkTutorCommand(nonExistentTutorName, DANIEL.getName());

        assertCommandFailure(command, model, String.format(LinkTutorCommand.MESSAGE_TUTOR_NOT_FOUND,
                nonExistentTutorName));
    }


    @Test
    public void equals() {
        LinkTutorCommand linkAliceToFiona = new LinkTutorCommand(ALICE.getName(), FIONA.getName());
        LinkTutorCommand linkBensonToFiona = new LinkTutorCommand(BENSON.getName(), FIONA.getName());

        // same object -> returns true
        assertTrue(linkAliceToFiona.equals(linkAliceToFiona));

        // same values -> returns true
        LinkTutorCommand linkAliceToFionaCopy = new LinkTutorCommand(ALICE.getName(), FIONA.getName());
        assertTrue(linkAliceToFiona.equals(linkAliceToFionaCopy));

        // different types -> returns false
        assertFalse(linkAliceToFiona.equals(1));

        // null -> returns false
        assertFalse(linkAliceToFiona.equals(null));

        // different student -> returns false
        assertFalse(linkAliceToFiona.equals(linkBensonToFiona));
    }
}
