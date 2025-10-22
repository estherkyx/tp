package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

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

public class UnlinkClassCommandTest {

    @Test
    public void execute_unlinkAlreadyUnlinkedStudent_failure() {
        TuitionClass classToUnlink = new TuitionClassBuilder().build();
        Person linkedStudent = new PersonBuilder().withCategory("student").withName("Greg Puciato").build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(linkedStudent).withTuitionClass(classToUnlink).build(),
                new UserPrefs());

        UnlinkClassCommand command = new UnlinkClassCommand(classToUnlink.getDay(), classToUnlink.getTime(),
                linkedStudent.getName());
        assertCommandFailure(command, model, UnlinkClassCommand.MESSAGE_STUDENT_NOT_IN_CLASS);

    }

    @Test
    public void execute_unlinkNotTutorOrStudent_failure() {
        Person parent = new PersonBuilder().withCategory("parent").withName("Daniel Meier").build();
        TuitionClass classToUnlink = new TuitionClassBuilder().build();
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(parent).withTuitionClass(classToUnlink).build(),
                new UserPrefs());
        UnlinkClassCommand command = new UnlinkClassCommand(classToUnlink.getDay(), classToUnlink.getTime(),
                parent.getName());
        assertCommandFailure(command, model, UnlinkClassCommand.MESSAGE_PERSON_NOT_STUDENT_OR_TUTOR);
    }

    @Test
    public void execute_classNotFound_failure() {
        Person student = new PersonBuilder().withCategory("student").build();
        Model model = new ModelManager(new AddressBookBuilder().withPerson(student).build(), new UserPrefs());
        UnlinkClassCommand command = new UnlinkClassCommand(Day.TUESDAY, Time.H12, student.getName());
        assertCommandFailure(command, model, UnlinkClassCommand.MESSAGE_CLASS_NOT_FOUND);

    }

    @Test
    public void execute_personNotFound_failure() {
        TuitionClass classToUnlink = new TuitionClassBuilder().build();
        Model model = new ModelManager(new AddressBookBuilder().withTuitionClass(classToUnlink).build(),
                new UserPrefs());
        Name nonExistentName = new Name("Mister Nobody");
        UnlinkClassCommand command = new UnlinkClassCommand(classToUnlink.getDay(), classToUnlink.getTime(),
                nonExistentName);
        assertCommandFailure(command, model, Messages.messagePersonNotFound(nonExistentName));

    }

    @Test
    public void execute_unlinkStudent_success() {
        TuitionClass classToUnlink = new TuitionClassBuilder().build();
        Person linkedStudent = new PersonBuilder().withCategory("student").withName("Benson Meier")
                .withTuitionClass(classToUnlink).build();
        classToUnlink.addStudentId(linkedStudent.getId());
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(linkedStudent).withTuitionClass(classToUnlink).build(),
                new UserPrefs());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student unlinkedStudent = (Student) linkedStudent;
        unlinkedStudent.clearTuitionClass();
        expectedModel.setPerson(linkedStudent, unlinkedStudent);

        String timeString = classToUnlink.getTime().toString().substring(1) + "00";
        String expectedMessage = String.format(UnlinkClassCommand.MESSAGE_UNLINK_STUDENT_SUCCESS,
                unlinkedStudent.getName(), classToUnlink.getDay(), timeString);

        UnlinkClassCommand command = new UnlinkClassCommand(classToUnlink.getDay(), classToUnlink.getTime(),
                linkedStudent.getName());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
