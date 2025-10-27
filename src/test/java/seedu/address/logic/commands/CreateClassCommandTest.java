package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;
import seedu.address.testutil.TuitionClassBuilder;

public class CreateClassCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_newClass_success() {
        // Create a class that doesn't exist in the model yet
        TuitionClass validClass = new TuitionClassBuilder().withDay(Day.FRIDAY).withTime(Time.H20).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTuitionClass(validClass);

        CreateClassCommand command = new CreateClassCommand(validClass.getDay(), validClass.getTime());

        String expectedMessage = String.format(
                CreateClassCommand.MESSAGE_SUCCESS, validClass.getDay(), validClass.getTime().toDisplayString());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateClass_throwsCommandException() {
        // Create a class and add it to the model first
        TuitionClass classInList = new TuitionClassBuilder().build();
        model.addTuitionClass(classInList);

        // Try to create the same class again
        CreateClassCommand command = new CreateClassCommand(classInList.getDay(), classInList.getTime());
        assertCommandFailure(command, model, CreateClassCommand.MESSAGE_DUPLICATE_CLASS);
    }

    @Test
    public void equals() {
        CreateClassCommand mondayClassCmd = new CreateClassCommand(Day.MONDAY, Time.H12);
        CreateClassCommand tuesdayClassCmd = new CreateClassCommand(Day.TUESDAY, Time.H12);

        // same object -> returns true
        assertEquals(mondayClassCmd, mondayClassCmd);

        // same values -> returns true
        CreateClassCommand mondayClassCmdCopy = new CreateClassCommand(Day.MONDAY, Time.H12);
        assertEquals(mondayClassCmd, mondayClassCmdCopy);

        // null -> returns false
        assertNotEquals(null, mondayClassCmd);

        // different class -> returns false
        assertNotEquals(mondayClassCmd, tuesdayClassCmd);
    }
}
