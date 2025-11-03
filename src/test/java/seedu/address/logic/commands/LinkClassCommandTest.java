package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TuitionClassBuilder;

/**
 * Contains integration tests (interaction with the Model) for LinkClassCommand.
 */
public class LinkClassCommandTest {

    @Test
    public void execute_linkUnlinkedStudentToClass_success() throws Exception {
        Person student = new PersonBuilder().withCategory("student").withName("Benson Meier").build();
        Person tutor = new PersonBuilder().withCategory("tutor").withName("Fiona Kunz").build();
        TuitionClass classToLink = new TuitionClassBuilder().withTutor(tutor).build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(student).withPerson(tutor)
                        .withTuitionClass(classToLink).build(),
                new UserPrefs());

        LinkClassCommand command = new LinkClassCommand(classToLink.getDay(), classToLink.getTime(), student.getName());
        CommandResult commandResult = command.execute(model);

        String expectedMessage = String.format(LinkClassCommand.MESSAGE_LINK_STUDENT_SUCCESS,
                student.getName(), classToLink.getDay(), classToLink.getTime().toDisplayString());
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        Student updatedStudent = (Student) model.findPersonByName(student.getName()).get(0);
        assertTrue(updatedStudent.getClassId().isPresent());
        assertEquals(classToLink.getClassId(), updatedStudent.getClassId().get());

        TuitionClass updatedClass = model.findTuitionClass(classToLink.getClassId()).get();
        assertTrue(updatedClass.getStudentIds().contains(student.getId()));
    }

    @Test
    public void execute_linkAlreadyLinkedStudent_failure() {
        TuitionClass classToLink = new TuitionClassBuilder().build();
        Person linkedStudent = new PersonBuilder().withCategory("student").withName("Benson Meier")
                .withTuitionClass(classToLink).build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(linkedStudent).withTuitionClass(classToLink).build(),
                new UserPrefs());
        LinkClassCommand command = new LinkClassCommand(classToLink.getDay(), classToLink.getTime(),
                linkedStudent.getName());
        assertCommandFailure(command, model,
                String.format(LinkClassCommand.MESSAGE_STUDENT_ALREADY_LINKED, classToLink.toSimpleString()));
    }

    @Test
    public void execute_assignTutorToClassWithNoTutor_success() throws Exception {
        TuitionClass classWithoutTutor = new TuitionClassBuilder().withTutor(null).build();
        Person tutorToAssign = new PersonBuilder().withCategory("tutor").withName("George Best").build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(tutorToAssign).withTuitionClass(classWithoutTutor).build(),
                new UserPrefs());

        LinkClassCommand command = new LinkClassCommand(classWithoutTutor.getDay(), classWithoutTutor.getTime(),
                tutorToAssign.getName());
        CommandResult commandResult = command.execute(model);

        String expectedMessage = String.format(LinkClassCommand.MESSAGE_ASSIGN_TUTOR_SUCCESS,
                tutorToAssign.getName(), classWithoutTutor.getDay(), classWithoutTutor.getTime().toDisplayString());
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        TuitionClass updatedClass = model.findTuitionClass(classWithoutTutor.getClassId()).get();
        assertEquals(tutorToAssign.getId(), updatedClass.getTutorId());
    }

    @Test
    public void execute_assignTutorToClassThatHasTutor_failure() {
        Person initialTutor = new PersonBuilder().withCategory("tutor").withName("Fiona Kunz").build();
        Person newTutor = new PersonBuilder().withCategory("tutor").withName("George Best").build();
        TuitionClass classToLink = new TuitionClassBuilder().withTutor(initialTutor).build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(initialTutor).withPerson(newTutor)
                        .withTuitionClass(classToLink).build(),
                new UserPrefs());
        LinkClassCommand command = new LinkClassCommand(classToLink.getDay(),
                classToLink.getTime(), newTutor.getName());
        String expectedErrorMessage = String.format(LinkClassCommand.MESSAGE_CLASS_ALREADY_HAS_TUTOR,
                initialTutor.getName().toString());
        assertCommandFailure(command, model, expectedErrorMessage);
    }

    @Test
    public void execute_reassignSameTutorToClass_failure() {
        Person tutor = new PersonBuilder().withCategory("tutor").withName("Fiona Kunz").build();
        TuitionClass classToLink = new TuitionClassBuilder().withTutor(tutor).build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(tutor).withTuitionClass(classToLink).build(),
                new UserPrefs());
        LinkClassCommand command = new LinkClassCommand(classToLink.getDay(), classToLink.getTime(), tutor.getName());
        assertCommandFailure(command, model, LinkClassCommand.MESSAGE_TUTOR_ALREADY_TEACHING);
    }

    // --- General Failure Scenarios ---

    @Test
    public void execute_classNotFound_failure() {
        Person student = new PersonBuilder().withCategory("student").build();
        Model model = new ModelManager(new AddressBookBuilder().withPerson(student).build(), new UserPrefs());
        LinkClassCommand command = new LinkClassCommand(Day.TUESDAY, Time.H12, student.getName());
        assertCommandFailure(command, model, LinkClassCommand.MESSAGE_CLASS_NOT_FOUND);
    }

    @Test
    public void execute_personNotFound_failure() {
        TuitionClass classToLink = new TuitionClassBuilder().build();
        Model model = new ModelManager(new AddressBookBuilder().withTuitionClass(classToLink).build(), new UserPrefs());
        Name nonExistentName = new Name("Mister Nobody");
        LinkClassCommand command = new LinkClassCommand(classToLink.getDay(), classToLink.getTime(), nonExistentName);
        assertCommandFailure(command, model, Messages.messagePersonNotFound(nonExistentName));
    }

    @Test
    public void execute_linkInvalidPersonType_failure() {
        Person parent = new PersonBuilder().withCategory("parent").withName("Daniel Meier").build();
        TuitionClass classToLink = new TuitionClassBuilder().build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(parent).withTuitionClass(classToLink).build(),
                new UserPrefs());
        LinkClassCommand command = new LinkClassCommand(classToLink.getDay(), classToLink.getTime(), parent.getName());
        assertCommandFailure(command, model, LinkClassCommand.MESSAGE_PERSON_NOT_STUDENT_OR_TUTOR);
    }
}
