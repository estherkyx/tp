package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Category;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_presentCategory_showsOnlyThatCategory() {
        ListCommand cmd = new ListCommand(Category.STUDENT);

        expectedModel.updateFilteredPersonList(p -> p.getCategory() == Category.STUDENT);

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, Category.STUDENT);
        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyCategory_showsEmptyList() {
        Category emptyCategory = Category.PARENT;
        AddressBook ab = new AddressBookBuilder().withoutCategory(emptyCategory).build();
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(ab, new UserPrefs());

        ListCommand cmd = new ListCommand(emptyCategory);

        expectedModel.updateFilteredPersonList(p -> false);
        String expectedMessage = String.format(ListCommand.MESSAGE_EMPTY_CATEGORY, emptyCategory.toString());

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listAll1 = new ListCommand();
        ListCommand listAll2 = new ListCommand();

        ListCommand listStudents1 = new ListCommand(Category.STUDENT);
        ListCommand listStudents2 = new ListCommand(Category.STUDENT);
        ListCommand listTutors = new ListCommand(Category.TUTOR);

        // same object
        assertTrue(listAll1.equals(listAll1));
        assertTrue(listStudents1.equals(listStudents1));

        // same values
        assertTrue(listAll1.equals(listAll2));
        assertTrue(listStudents1.equals(listStudents2));

        // different values
        assertFalse(listAll1.equals(listStudents1));
        assertFalse(listStudents1.equals(listTutors));

        // null & different type
        assertFalse(listAll1.equals(null));
        assertFalse(listAll1.equals("not a command"));
    }
}
