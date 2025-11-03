package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Optional;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.Category;
import seedu.address.model.person.PersonId;

/**
 * Lists people in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all people, or only those in a particular category if specified. \n"
            + "Parameters: [" + PREFIX_CATEGORY + "*CATEGORY]\n"
            + "Example 1: " + COMMAND_WORD + "\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_CATEGORY + "student";

    public static final String MESSAGE_SUCCESS = "Listed all people in the '%s' category.\n\n" +
            "(To get back to the full list of contacts, run the 'list' command)";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all people in the address book.";

    public static final String MESSAGE_MISSING_CATEGORY = "Category is not stated. Use student, tutor or parent.";

    public static final String MESSAGE_EMPTY_CATEGORY = "There is no one belonging to the category '%s'.\n\n" +
            "(To get back to the full list of contacts, run the 'list' command)";

    public static final String MESSAGE_INVALID_CATEGORY = "Invalid category. Use student, tutor or parent.";

    public final Optional<Category> category;

    /**
     * Creates a command to list all people in the address book.
     */
    public ListCommand() {
        this.category = Optional.empty();
    }

    /**
     * Creates a command to list all persons of a specified category.
     * @param category of the list of people to retrieve
     */
    public ListCommand(Category category) {
        requireNonNull(category);
        this.category = Optional.of(category);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // list without category
        if (category.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }

        Category cat = category.get();
        Set<PersonId> listedPersons = model.getPersonIdsByCategory(cat);

        // Category has no one
        if (listedPersons.isEmpty()) {
            model.updateFilteredPersonList(p -> false);
            return new CommandResult(String.format(MESSAGE_EMPTY_CATEGORY, cat.toString()));
        }

        // Update UI to show filtered list
        model.updateFilteredPersonList(p -> listedPersons.contains(p.getId()));

        return new CommandResult(String.format(MESSAGE_SUCCESS, cat.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        // state check
        ListCommand e = (ListCommand) other;
        return category.equals(e.category);
    }
}
