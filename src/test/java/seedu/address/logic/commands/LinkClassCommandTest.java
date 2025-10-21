package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TuitionClassBuilder;

/**
 * Contains integration tests (interaction with the Model) for LinkClassCommand.
 */
public class LinkClassCommandTest { //todo: missing tests for linking student and tutor

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
        assertCommandFailure(command, model, LinkClassCommand.MESSAGE_STUDENT_ALREADY_LINKED);
    }


    @Test
    public void execute_reassignSameTutorToClass_failure() {
        // ARRANGE: Create a class with a tutor.
        Person tutor = new PersonBuilder().withCategory("tutor").withName("Fiona Kunz").build();
        TuitionClass classToLink = new TuitionClassBuilder().withTutor(tutor).build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(tutor).withTuitionClass(classToLink).build(),
                new UserPrefs());

        // ACT & ASSERT: Try to assign the same tutor again.
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
